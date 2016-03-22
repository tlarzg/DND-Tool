package com.rprescott.dndtool.sharedmessages.login;

import java.io.Serializable;

public class InitiateLoginResponse implements Serializable {
    private static final long serialVersionUID = -4023484984129887481L;
    private final String saltHex;
    private final String serverPublicHex;

    public InitiateLoginResponse(String saltHex, String serverPublicHex) {
        this.saltHex = saltHex;
        this.serverPublicHex = serverPublicHex;
    }

    public String getSaltHex() {
        return saltHex;
    }

    public String getServerPublicHex() {
        return serverPublicHex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((saltHex == null) ? 0 : saltHex.hashCode());
        result = prime * result + ((serverPublicHex == null) ? 0 : serverPublicHex.hashCode());
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
        if (!(obj instanceof InitiateLoginResponse)) {
            return false;
        }
        InitiateLoginResponse other = (InitiateLoginResponse) obj;
        if (saltHex == null) {
            if (other.saltHex != null) {
                return false;
            }
        } else if (!saltHex.equals(other.saltHex)) {
            return false;
        }
        if (serverPublicHex == null) {
            if (other.serverPublicHex != null) {
                return false;
            }
        } else if (!serverPublicHex.equals(other.serverPublicHex)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InitiateLoginResponse [saltHex=");
        builder.append(saltHex);
        builder.append(", serverPublicHex=");
        builder.append(serverPublicHex);
        builder.append("]");
        return builder.toString();
    }
}