package com.dndtool.server.security;

import java.io.Serializable;

public class RegistrationDetails implements Serializable {

    private static final long serialVersionUID = -114291528799541941L;
    private String userName;
    private String password;
    
    public RegistrationDetails() {
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
