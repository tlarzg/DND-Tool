package com.dndtool.server.security;

import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsDAO {

    CredentialsDTO getUserCredentialByName(String userName);

    void registerNewUser(CredentialsDTO credentials);
}
