package com.rprescott.dndtool.server.database.login;

import org.apache.ibatis.annotations.Param;

public interface LoginDAO {

    /**
     * Returns the credentials for the supplied user name.
     * 
     * @param userName - The user name to find credentials for.
     * @return The credentials for the user or <b>null</b> if the user doesn't exist.
     */
    CredentialDTO getUserCredentialByName(@Param("userName") String userName);
    
    void registerNewUser(CredentialDTO credentials);

}
