package com.rprescott.dndtool.sharedmessages.registration;

import java.io.Serializable;

import com.rprescott.dndtool.sharedmessages.Credentials;

public class NewUserRegistration extends Credentials implements Serializable {
    
    private static final long serialVersionUID = -1396007678499225896L;

    public NewUserRegistration(String userName, char[] password) {
        this.userName = userName;
        this.password = password;
    }
}
