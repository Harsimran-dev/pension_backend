package com.pensionbackend.entities;

import java.util.Collection;
import java.util.List;
import jakarta.persistence.GeneratedValue;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import com.pensionbackend.enums.Role;

import jakarta.persistence.*;


import lombok.Data;


@Entity

@Data

@Table(name = "users")
public class PensionUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private boolean mfaEnabled;
    private String secret;
    private String qrCodeUri;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
