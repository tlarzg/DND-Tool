package com.rprescott.dndtool.sharedmessages;

import java.io.Serializable;

public class Credentials implements Serializable {
    
    private static final long serialVersionUID = -58305051875526126L;
    protected String userName;
    protected char[] password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

}
