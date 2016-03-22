package com.rprescott.dndtool.sharedmessages.login;

import java.io.Serializable;

public class InitiateLogin implements Serializable {
    private static final long serialVersionUID = -740383304815911868L;
    private final String userName;

    public InitiateLogin(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof InitiateLogin)) {
            return false;
        }

        InitiateLogin other = (InitiateLogin) obj;
        if (userName == null) {
            if (other.userName != null) {
                return false;
            }
        } else if (!userName.equals(other.userName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InitiateLogin [userName=");
        builder.append(userName);
        builder.append("]");
        return builder.toString();
    }
}