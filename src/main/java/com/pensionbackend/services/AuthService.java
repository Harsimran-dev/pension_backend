package com.pensionbackend.services;

import java.util.List;

import com.pensionbackend.dtos.AuthResponse;
import com.pensionbackend.dtos.PensionUserDto;
import com.pensionbackend.dtos.VerfRequest;
import com.pensionbackend.entities.PensionUser;

public interface AuthService {

    PensionUserDto createUser(PensionUser user);
    AuthResponse verifyCode(VerfRequest verificationRequest);
    void deleteUserById(Long userId);
    List<PensionUser> listAllUsers();



 
}