package com.rprescott.dndtool.sharedmessages.login;

import java.io.Serializable;

public class ClientLoginEvidence implements Serializable {
    private static final long serialVersionUID = -7285887426572722776L;
    private final String clientPublicHex;
    private final String clientEvidenceHex;

    public ClientLoginEvidence(String clientPublicHex, String clientEvidenceHex) {
        this.clientPublicHex = clientPublicHex;
        this.clientEvidenceHex = clientEvidenceHex;
    }

    public String getClientPublicHex() {
        return clientPublicHex;
    }

    public String getClientEvidenceHex() {
        return clientEvidenceHex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientEvidenceHex == null) ? 0 : clientEvidenceHex.hashCode());
        result = prime * result + ((clientPublicHex == null) ? 0 : clientPublicHex.hashCode());
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
        if (clientPublicHex == null) {
            if (other.clientPublicHex != null) {
                return false;
            }
        } else if (!clientPublicHex.equals(other.clientPublicHex)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClientLoginEvidence [clientPublicHex=");
        builder.append(clientPublicHex);
        builder.append(", clientEvidenceHex=");
        builder.append(clientEvidenceHex);
        builder.append("]");
        return builder.toString();
    }
}
