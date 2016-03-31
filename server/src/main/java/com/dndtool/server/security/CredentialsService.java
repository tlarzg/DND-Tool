package com.dndtool.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService implements UserDetailsService {

    private final CredentialsDAO credentialsDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    CredentialsService(CredentialsDAO credentialsDao, PasswordEncoder passwordEncoder) {
        this.credentialsDao = credentialsDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsDao.getUserCredentialByName(username);
    }
    
    /**
     * Determines whether a user exists or not.
     * 
     * @param username - The user to check.
     * @return True if the user exists. False otherwise.
     */
    public boolean userExists(String username) {
        return credentialsDao.getUserCredentialByName(username) != null;
    }

    public void registerUser(String username, String password) {
        credentialsDao.registerNewUser(username, passwordEncoder.encode(password));
    }
}
