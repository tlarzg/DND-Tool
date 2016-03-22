package com.rprescott.dndtool.sharedmessages.login;

import java.io.Serializable;

import com.rprescott.dndtool.sharedmessages.Credentials;
import com.rprescott.dndtool.sharedmessages.markers.ClientMessage;

public class UserLogin extends Credentials implements Serializable, ClientMessage {

    private static final long serialVersionUID = 2839677747697635050L;

    public UserLogin(String userName, char[] password) {
        this.userName = userName;
        this.password = password;
    }

}
