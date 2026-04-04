package com.opticalshop.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Principal được set vào SecurityContext sau khi JWT hợp lệ.
 * getUsername() trả về userId (UUID string) để controller parse thành UUID.
 */
public class CustomUserPrincipal implements UserDetails {

    private final String userId;
    private final String username;

    public CustomUserPrincipal(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /** Trả về userId (UUID string) — controller dùng cái này để extract UUID */
    @Override
    public String getUsername() {
        return userId;
    }

    public String getDisplayUsername() {
        return username;
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override public boolean isAccountNonExpired()  { return true; }
    @Override public boolean isAccountNonLocked()   { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()            { return true; }
}
