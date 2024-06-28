package com.pensionbackend.services;

import java.util.Optional;

import com.pensionbackend.dtos.PersonalDetailsDTO;

import com.pensionbackend.entities.PersonalDetails;



public interface PensionUserDetailsService {
    PersonalDetails createUserDetails(PersonalDetailsDTO userDetailsDTO);
    Optional<PersonalDetails> getByUserId(Long userId);
    PersonalDetails updateUserDetails(Long userId, PersonalDetailsDTO userDetailsDTO);
}
