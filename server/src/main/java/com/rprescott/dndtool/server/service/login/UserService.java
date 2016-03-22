package com.rprescott.dndtool.server.service.login;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.database.login.CredentialDTO;
import com.rprescott.dndtool.server.database.login.LoginDAO;

@Service
public class UserService {
    //XXX: Load from external secret store
    private static final String VERIFICATION_SECRET_KEY = "AG#%JAsadgSD3456POIJAS234GIHAS!DVOJsdiohsa&oidgh/lk21j34235";
    //XXX: Load from external secret store
    private static final String SALT_SECRET_KEY = "V OSO s9e0utw et #4t6346jos//[lSDGW#$)sviojhsg90u23SGKNLHvspog235";

    @Autowired
    private LoginDAO loginDao;

    /**
     * Determines whether the supplied user exists in the database or not.
     *
     * @param userName - The user name to check.
     * @return True if the user exists in the database. False otherwise.
     */
    public boolean userExists(String userName) {
        boolean ret = false;
        if (getUser(userName) != null) {
            System.out.println("User: " + userName + " exists in the database.");
            ret = true;
        }
        else {
            System.out.println("User: " + userName + " does not exist in the database.");
        }
        return ret;
    }

    /**
     * Wrapper to the database mapper method to obtain the specified user from the database.
     *
     * @param userName - The user name to check.
     * @return The user credentials from the database or <b>null</b> if the user does not exist.
     */
    public CredentialDTO getUser(String userName) {
        return loginDao.getUserCredentialByName(userName);
    }

    public CredentialDTO registerUser(String userName, BigInteger verificationSalt, BigInteger verificationSecret) {
        CredentialDTO credentials = new CredentialDTO();
        credentials.setUserName(userName);;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] generatedIv = new byte[cipher.getBlockSize()];
            new SecureRandom().nextBytes(generatedIv);
            credentials.setCipherIv(generatedIv);
            IvParameterSpec ivSpec = new IvParameterSpec(generatedIv);

            SecretKeySpec keySpec = getKeyFromSecret(VERIFICATION_SECRET_KEY);
            credentials.setCipheredVerificationSecret(
                encryptWithCipher(keySpec, ivSpec, verificationSecret.toByteArray()));

            keySpec = getKeyFromSecret(SALT_SECRET_KEY);
            credentials.setCipheredVerificationSalt(
                encryptWithCipher(keySpec, ivSpec, verificationSalt.toByteArray()));
        }
        catch (GeneralSecurityException ex) {
            throw new RuntimeException("Could not digest user credentials", ex);
        }

        loginDao.registerNewUser(credentials);

        return credentials;
    }

    public Optional<BigInteger> getVerificationSecret(CredentialDTO userCredentials) {
        Optional<BigInteger> verificationSecret;
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(userCredentials.getCipherIv());
            SecretKeySpec keySpec = getKeyFromSecret(VERIFICATION_SECRET_KEY);

            byte[] secretBytes = decryptWithCipher(keySpec, ivSpec, userCredentials.getCipheredVerificationSecret());
            verificationSecret = Optional.of(new BigInteger(secretBytes));
        }
        catch (GeneralSecurityException ex) {
            ex.printStackTrace();
            verificationSecret = Optional.empty();
        }

        return verificationSecret;
    }

    public Optional<BigInteger> getVerificationSalt(CredentialDTO userCredentials) {
        Optional<BigInteger> verificationSalt;
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(userCredentials.getCipherIv());
            SecretKeySpec keySpec = getKeyFromSecret(SALT_SECRET_KEY);

            byte[] secretBytes = decryptWithCipher(keySpec, ivSpec, userCredentials.getCipheredVerificationSalt());
            verificationSalt = Optional.of(new BigInteger(secretBytes));
        }
        catch (GeneralSecurityException ex) {
            ex.printStackTrace();
            verificationSalt = Optional.empty();
        }

        return verificationSalt;
    }

    private SecretKeySpec getKeyFromSecret(String secret) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(SALT_SECRET_KEY.getBytes());
        return new SecretKeySpec(digest.digest(), "AES");
    }

    private byte[] encryptWithCipher(SecretKeySpec keySpec, IvParameterSpec ivSpec, byte[] value)
        throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(value);
    }

    private byte[] decryptWithCipher(SecretKeySpec keySpec, IvParameterSpec ivSpec, byte[] value)
        throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(value);
    }
}