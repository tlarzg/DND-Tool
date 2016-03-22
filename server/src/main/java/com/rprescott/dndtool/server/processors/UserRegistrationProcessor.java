package com.rprescott.dndtool.server.processors;

import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.MessageProcessor;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistration;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistrationResponse;

@Service
public class UserRegistrationProcessor implements MessageProcessor<NewUserRegistration, NewUserRegistrationResponse> {

    @Override
    public NewUserRegistrationResponse processMessage(NewUserRegistration message) {
        System.out.println("Processing new user registration request.");
        return null;
    }

}
