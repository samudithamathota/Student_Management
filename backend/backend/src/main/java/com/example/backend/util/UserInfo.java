package com.example.backend.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfo implements UserDetails {

    private final String userId;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfo(String userId, String password, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.authorities = authorities;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }
}
