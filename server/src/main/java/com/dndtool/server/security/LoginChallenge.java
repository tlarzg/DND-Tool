package com.dndtool.server.security;

class LoginChallenge {
    private final String userSaltHex;
    private final String serverPublicValHex;
    private final String generatorValHex;
    private final String primeValHex;

    LoginChallenge(String userSaltHex, String serverPublicValHex, String generatorValHex, String primeValHex) {
        this.userSaltHex = userSaltHex;
        this.serverPublicValHex = serverPublicValHex;
        this.generatorValHex = generatorValHex;
        this.primeValHex = primeValHex;
    }

    public String getUserSaltHex() {
        return userSaltHex;
    }

    public String getServerPublicValHex() {
        return serverPublicValHex;
    }

    public String getGeneratorValHex() {
        return generatorValHex;
    }

    public String getPrimeValHex() {
        return primeValHex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((generatorValHex == null) ? 0 : generatorValHex.hashCode());
        result = prime * result + ((primeValHex == null) ? 0 : primeValHex.hashCode());
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
        if (generatorValHex == null) {
            if (other.generatorValHex != null) {
                return false;
            }
        } else if (!generatorValHex.equals(other.generatorValHex)) {
            return false;
        }
        if (primeValHex == null) {
            if (other.primeValHex != null) {
                return false;
            }
        } else if (!primeValHex.equals(other.primeValHex)) {
            return false;
        }
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
        builder.append("LoginChallenge [userSaltHex=");
        builder.append(userSaltHex);
        builder.append(", serverPublicValHex=");
        builder.append(serverPublicValHex);
        builder.append(", generatorValHex=");
        builder.append(generatorValHex);
        builder.append(", primeValHex=");
        builder.append(primeValHex);
        builder.append("]");
        return builder.toString();
    }
}
