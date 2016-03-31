package com.dndtool.server.security;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
interface CredentialsDAO {

    CredentialsDTO getUserCredentialByName(@Param("username") String username);

    void registerNewUser(@Param("username") String username, @Param("password") String encodedPassword);
}
