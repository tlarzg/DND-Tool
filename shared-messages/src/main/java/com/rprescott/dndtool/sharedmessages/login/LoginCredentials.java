package com.rprescott.dndtool.sharedmessages.login;

import java.io.Serializable;

public class LoginCredentials implements Serializable {

    private static final long serialVersionUID = -1053376166491854400L;
    private String userName;
    private String password;

    public LoginCredentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
