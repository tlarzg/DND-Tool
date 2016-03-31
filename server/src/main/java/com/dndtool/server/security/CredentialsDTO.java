package com.dndtool.server.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CredentialsDTO implements UserDetails {
    private static final long serialVersionUID = 6281155163931840906L;
    private Long id;
    private String username;
    private String password;
    private boolean isAccountExpired;
    private boolean isAccountLocked;
    private boolean isCredentialsExpired;
    private boolean isAccountEnabled;
    private static final List<GrantedAuthority> USER_AUTHORITY = AuthorityUtils.createAuthorityList("USER");

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    void setIsAccountExpired(boolean isAccountExpired) {
        this.isAccountExpired = isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    void setIsAccountLocked(boolean isAccountLocked) {
        this.isAccountLocked = isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    void setIsCredentialsExpired(boolean isCredentialsExpired) {
        this.isCredentialsExpired = isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isAccountEnabled;
    }

    void setIsAccountEnabled(boolean isAccountEnabled) {
        this.isAccountEnabled = isAccountEnabled;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return USER_AUTHORITY;
    }
}
