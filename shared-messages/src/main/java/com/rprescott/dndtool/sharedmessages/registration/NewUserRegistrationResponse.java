package com.rprescott.dndtool.sharedmessages.registration;

import java.io.Serializable;

import com.rprescott.dndtool.sharedmessages.markers.ServerMessage;

public class NewUserRegistrationResponse implements Serializable, ServerMessage {
    
    private static final long serialVersionUID = -4614785068698093019L;
    private boolean registrationSuccessful;

    public NewUserRegistrationResponse(boolean registrationSuccessful) {
        this.registrationSuccessful = registrationSuccessful;
    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    public void setRegistrationSuccessful(boolean registrationSuccessful) {
        this.registrationSuccessful = registrationSuccessful;
    }

}
