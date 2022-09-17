package com.billing.webapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
@Document
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;

    @Email
    private String email;

    private String password;

    private Set<? extends GrantedAuthority> role;

    @DBRef(lazy = true)
    private Set<Address> addresses;

    public User(String email, String password, Set<? extends GrantedAuthority> role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
