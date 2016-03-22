package com.rprescott.dndtool.sharedmessages.registration;

import java.io.Serializable;

import com.rprescott.dndtool.sharedmessages.markers.ClientMessage;

public class NewUserRegistration implements Serializable, ClientMessage {

    private static final long serialVersionUID = -1396007678499225896L;

    private final String userName;
    private final String verificationSecretHex;
    private final String verificationSaltHex;

    public NewUserRegistration(String userName, String verificationSecretHex, String verificationSaltHex) {
        this.userName = userName;
        this.verificationSecretHex = verificationSecretHex;
        this.verificationSaltHex = verificationSaltHex;
    }

    public String getUserName() {
        return userName;
    }

    public String getVerificationSecretHex() {
        return verificationSecretHex;
    }

    public String getVerificationSaltHex() {
        return verificationSaltHex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((verificationSaltHex == null) ? 0 : verificationSaltHex.hashCode());
        result = prime * result + ((verificationSecretHex == null) ? 0 : verificationSecretHex.hashCode());
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
        if (!(obj instanceof NewUserRegistration)) {
            return false;
        }

        NewUserRegistration other = (NewUserRegistration) obj;
        if (userName == null) {
            if (other.userName != null) {
                return false;
            }
        } else if (!userName.equals(other.userName)) {
            return false;
        }
        if (verificationSaltHex == null) {
            if (other.verificationSaltHex != null) {
                return false;
            }
        } else if (!verificationSaltHex.equals(other.verificationSaltHex)) {
            return false;
        }
        if (verificationSecretHex == null) {
            if (other.verificationSecretHex != null) {
                return false;
            }
        } else if (!verificationSecretHex.equals(other.verificationSecretHex)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NewUserRegistration [userName=");
        builder.append(userName);
        builder.append(", verificationSecretHex=");
        builder.append(verificationSecretHex);
        builder.append(", verificationSaltHex=");
        builder.append(verificationSaltHex);
        builder.append("]");
        return builder.toString();
    }
}
