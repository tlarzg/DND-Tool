package com.rprescott.dndtool.server.utils;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.rprescott.dndtool.server.database.login.CredentialDTO;

public class CredentialGenerator {
    
    public static CredentialDTO generateCredentials(String userName, char[] password) {
        CredentialDTO credentials = new CredentialDTO();
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
            SecretKeyFactory secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = secretKey.generateSecret(spec).getEncoded();
            Base64.Encoder base64Encoder = Base64.getEncoder();
            String hashedPassword = base64Encoder.encodeToString(hash);
            credentials.setSalt(salt);
            credentials.setHash(hashedPassword);
            credentials.setUserName(userName);
        }
        catch (Exception e) {
            credentials = null;
            e.printStackTrace();
        }
        return credentials;
    }
}
