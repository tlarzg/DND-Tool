package com.rprescott.dndtool.server.account;

public class ServerLoginEvidence {
    private final String serverEvidenceHex;

    ServerLoginEvidence(String serverEvidenceHex) {
        this.serverEvidenceHex = serverEvidenceHex;
    }

    public String getServerEvidenceHex() {
        return serverEvidenceHex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serverEvidenceHex == null) ? 0 : serverEvidenceHex.hashCode());
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
        if (!(obj instanceof ServerLoginEvidence)) {
            return false;
        }
        ServerLoginEvidence other = (ServerLoginEvidence) obj;
        if (serverEvidenceHex == null) {
            if (other.serverEvidenceHex != null) {
                return false;
            }
        } else if (!serverEvidenceHex.equals(other.serverEvidenceHex)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServerLoginEvidence [serverEvidenceHex=");
        builder.append(serverEvidenceHex);
        builder.append("]");
        return builder.toString();
    }
}
