package com.rprescott.dndtool.server.service.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.database.login.CredentialDTO;
import com.rprescott.dndtool.server.database.login.LoginDAO;
import com.rprescott.dndtool.server.utils.CredentialGenerator;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistration;

@Service
public class UserRegistrationService {
    
    @Autowired
    private LoginDAO loginDao;

    public boolean registerNewUser(NewUserRegistration registrationRequest) {
        boolean ret = true;
        // Check to see if a user already exists with that name.
        if (loginDao.getUserCredentialByName(registrationRequest.getUserName()) != null) {
            ret = false;
        }
        else {
            CredentialDTO credentials = CredentialGenerator.generateCredentials(registrationRequest.getUserName(), registrationRequest.getPassword());
            if (credentials == null) {
                ret = false;
            }
            else {
                System.out.println("Registering new user with name: " + credentials.getUserName());
                loginDao.registerNewUser(credentials);   
            }
        }
        
        return ret;
    }

}
