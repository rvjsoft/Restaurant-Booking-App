package com.rvj.app.security;

import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {
    private String userName;
    private String password;
    private UserLevel userLevel;

    public CustomUserDetails(String userName, String password, UserLevel userLevel) {
        this.userName = userName;
        this.password = password;
        this.userLevel = userLevel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "" + userLevel;
            }
        });
    }

    @Override
    public String getPassword() {
        return "{noop}" + this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Objects.nonNull(this.userName);
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.nonNull(this.userName);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Objects.nonNull(this.userName);
    }

    @Override
    public boolean isEnabled() {
        return Objects.nonNull(this.userName);
    }
}
