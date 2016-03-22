package com.rprescott.dndtool.server.processors;

import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.MessageProcessor;
import com.rprescott.dndtool.sharedmessages.login.LoginResponse;
import com.rprescott.dndtool.sharedmessages.login.UserLogin;

@Service
public class UserLoginProcessor implements MessageProcessor<UserLogin, LoginResponse> {

    @Override
    public LoginResponse processMessage(UserLogin message) {
        System.out.println("Processing login request.");
        return null;
    }

}
