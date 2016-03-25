package com.rprescott.dndtool.server.account;

class LoginChallenge {
    private final String userSaltHex;
    private final String serverPublicValHex;

    LoginChallenge(String userSaltHex, String serverPublicValHex) {
        this.userSaltHex = userSaltHex;
        this.serverPublicValHex = serverPublicValHex;
    }

    public String getUserSaltHex() {
        return userSaltHex;
    }

    public String getServerPublicValHex() {
        return serverPublicValHex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serverPublicValHex == null) ? 0 : serverPublicValHex.hashCode());
        result = prime * result + ((userSaltHex == null) ? 0 : userSaltHex.hashCode());
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
        if (!(obj instanceof LoginChallenge)) {
            return false;
        }
        LoginChallenge other = (LoginChallenge) obj;
        if (serverPublicValHex == null) {
            if (other.serverPublicValHex != null) {
                return false;
            }
        } else if (!serverPublicValHex.equals(other.serverPublicValHex)) {
            return false;
        }
        if (userSaltHex == null) {
            if (other.userSaltHex != null) {
                return false;
            }
        } else if (!userSaltHex.equals(other.userSaltHex)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LoginServerEvidence [userSaltHex=");
        builder.append(userSaltHex);
        builder.append(", serverPublicValHex=");
        builder.append(serverPublicValHex);
        builder.append("]");
        return builder.toString();
    }
}
