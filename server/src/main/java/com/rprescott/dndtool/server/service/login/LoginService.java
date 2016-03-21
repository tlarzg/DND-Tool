package com.rprescott.dndtool.server.service.login;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.database.login.CredentialDTO;
import com.rprescott.dndtool.server.database.login.LoginDAO;

@Service
public class LoginService {
    
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
    
    /**
     * Validates the supplied user name and password against stored database credentials.
     * 
     * @param userName - The user name to check.
     * @param password - The password to check.
     * @return True if a user exists with the supplied password. False otherwise.
     */
    public boolean validateUser(String userName, char[] password) {
        boolean validUser = false;
        // The user exists. Let's verify some passwords.
        try {
            // Grab the credentials of the user attempting to login.
            CredentialDTO trustedCredentials = getUser(userName);
            Base64.Encoder base64Encoder = Base64.getEncoder();
            KeySpec spec = new PBEKeySpec(password, trustedCredentials.getSalt(), 65536, 128);
            SecretKeyFactory secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            // Using the salt created when registering the user, rehash the supplied password with the same salt.
            byte[] hash = secretKey.generateSecret(spec).getEncoded();
            String hashedPassword = base64Encoder.encodeToString(hash);
            
            System.out.println("Trusted Credentials: " + trustedCredentials.getHash());
            System.out.println("Hash value of supplied password: " + hashedPassword);
            
            // If the trusted credentials hash to the same value as the password using the supplied salt, then the password
            // supplied in the request must be the same as the registered one.
            if (trustedCredentials.getHash().equals(hashedPassword)) {
                validUser = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return validUser;
    }
    
}
