package com.rprescott.dndtool.server.processors;

import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.MessageProcessor;
import com.rprescott.dndtool.sharedmessages.login.InitiateLogin;
import com.rprescott.dndtool.sharedmessages.login.LoginResponse;

@Service
public class UserLoginProcessor implements MessageProcessor<InitiateLogin, LoginResponse> {

    @Override
    public LoginResponse processMessage(InitiateLogin message) {
        System.out.println("Processing login request.");
        return null;
    }

}
