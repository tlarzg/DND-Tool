package com.rprescott.dndtool.server.account;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO {
    /**
     * Returns the credentials for the supplied user name.
     *
     * @param userName - The user name to find credentials for.
     * @return The credentials for the user or <b>null</b> if the user doesn't exist.
     */
    AccountCredentialsDTO getUserCredentialByName(@Param("userName") String userName);

    void registerNewUser(AccountCredentialsDTO credentials);
}
