package com.pensionbackend.services;


import com.pensionbackend.dtos.PersonalDetailsDTO;
import com.pensionbackend.entities.PersonalDetails;

import com.pensionbackend.repositories.UserDetailsRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PensionUserDetailsServiceImpl implements PensionUserDetailsService {

    private final UserDetailsRepository personalDetailsRepository;

    @Autowired
    public PensionUserDetailsServiceImpl(UserDetailsRepository personalDetailsRepository) {
        this.personalDetailsRepository = personalDetailsRepository;
    }

    @Override
    public PersonalDetails createUserDetails(PersonalDetailsDTO userDetailsDTO) {
        PersonalDetails userDetails = new PersonalDetails();
   
        userDetails.setFullName(userDetailsDTO.getFullName());
        userDetails.setDateOfBirth(userDetailsDTO.getDateOfBirth());
        userDetails.setGender(userDetailsDTO.getGender());
        userDetails.setNationality(userDetailsDTO.getNationality());

        userDetails.setEducation(userDetailsDTO.getEducation());
        userDetails.setNiNumber(userDetailsDTO.getNiNumber());
        userDetails.setUserId(userDetailsDTO.getUserId());

        
        return personalDetailsRepository.save(userDetails);
    }


    @Override
    public Optional<PersonalDetails> getByUserId(Long userId) {
        return personalDetailsRepository.findByUserId(userId);
    }


    @Override
    public PersonalDetails updateUserDetails(Long userId, PersonalDetailsDTO userDetailsDTO) {
        Optional<PersonalDetails> optionalUserDetails = personalDetailsRepository.findById(userId);
        if (optionalUserDetails.isPresent()) {
            PersonalDetails userDetails = optionalUserDetails.get();
            userDetails.setProfilePhoto(userDetailsDTO.getProfilePhoto());
            userDetails.setFullName(userDetailsDTO.getFullName());
            userDetails.setDateOfBirth(userDetailsDTO.getDateOfBirth());
            userDetails.setGender(userDetailsDTO.getGender());
            userDetails.setNationality(userDetailsDTO.getNationality());
            userDetails.setEducation(userDetailsDTO.getEducation());
            userDetails.setNiNumber(userDetailsDTO.getNiNumber());
            return personalDetailsRepository.save(userDetails);
        } else {
            throw new RuntimeException("User details not found for user ID: " + userId);
        }
    }
}
