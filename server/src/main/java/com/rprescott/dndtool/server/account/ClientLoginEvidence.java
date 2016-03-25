package com.rprescott.dndtool.server.account;

public class ClientLoginEvidence {
    private String clientPublicValHex;
    private String clientEvidenceHex;

    public String getClientPublicValHex() {
        return clientPublicValHex;
    }

    public void setClientPublicValHex(String clientPublicValHex) {
        this.clientPublicValHex = clientPublicValHex;
    }

    public String getClientEvidenceHex() {
        return clientEvidenceHex;
    }

    public void setClientEvidenceHex(String clientEvidenceHex) {
        this.clientEvidenceHex = clientEvidenceHex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientEvidenceHex == null) ? 0 : clientEvidenceHex.hashCode());
        result = prime * result + ((clientPublicValHex == null) ? 0 : clientPublicValHex.hashCode());
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
        if (!(obj instanceof ClientLoginEvidence)) {
            return false;
        }
        ClientLoginEvidence other = (ClientLoginEvidence) obj;
        if (clientEvidenceHex == null) {
            if (other.clientEvidenceHex != null) {
                return false;
            }
        } else if (!clientEvidenceHex.equals(other.clientEvidenceHex)) {
            return false;
        }
        if (clientPublicValHex == null) {
            if (other.clientPublicValHex != null) {
                return false;
            }
        } else if (!clientPublicValHex.equals(other.clientPublicValHex)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClientLoginEvidence [clientPublicValHex=");
        builder.append(clientPublicValHex);
        builder.append(", clientEvidenceHex=");
        builder.append(clientEvidenceHex);
        builder.append("]");
        return builder.toString();
    }
}
