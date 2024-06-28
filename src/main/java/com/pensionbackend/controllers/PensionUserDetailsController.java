package com.pensionbackend.controllers;

import com.pensionbackend.dtos.PersonalDetailsDTO;
import com.pensionbackend.entities.PersonalDetails;
import com.pensionbackend.services.PensionUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/user-details")
public class PensionUserDetailsController {

    private final PensionUserDetailsService userDetailsService;

    @Autowired
    public PensionUserDetailsController(PensionUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/create")
    public ResponseEntity<PersonalDetails> createUserDetails(@RequestBody PersonalDetailsDTO userDetailsDTO) {
        PersonalDetails userDetails = userDetailsService.createUserDetails(userDetailsDTO);
        return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PersonalDetails> getUserDetailsByUserId(@PathVariable Long userId) {
        Optional<PersonalDetails> userDetails = userDetailsService.getByUserId(userId);
        return userDetails.map(personalDetails -> new ResponseEntity<>(personalDetails, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<PersonalDetails> updateUserDetails(@PathVariable Long userId, @RequestBody PersonalDetailsDTO userDetailsDTO) {
        PersonalDetails updatedDetails = userDetailsService.updateUserDetails(userId, userDetailsDTO);
        if (updatedDetails != null) {
            return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
