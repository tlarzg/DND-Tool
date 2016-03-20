package com.rprescott.dndtool.server.database.login;

public interface LoginDAO {

    /**
     * Returns the credentials for the supplied user name.
     * 
     * @param userName - The user name to find credentials for.
     * @return The credentials for the user or <b>null</b> if the user doesn't exist.
     */
    CredentialDTO getUserCredentialByName(String userName);

}
