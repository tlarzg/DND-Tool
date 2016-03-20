package com.rprescott.dndtool.sharedmessages.login;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    
    private static final long serialVersionUID = 4216214815596755635L;
    private boolean loginSuccessful;

    public LoginResponse(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

}
