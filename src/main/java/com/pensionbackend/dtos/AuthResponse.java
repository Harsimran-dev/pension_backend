package com.pensionbackend.dtos;

import com.pensionbackend.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwt;
    private Role userRole;
    private Long userId;
    private String email;

   
    private boolean mfaEnabled;
}