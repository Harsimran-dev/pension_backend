package com.pensionbackend.controllers;

import com.pensionbackend.dtos.DefinedContributionDTO;
import com.pensionbackend.entities.DefinedContribution;
import com.pensionbackend.services.DefinedContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/defined-contributions")
public class DefinedContributionController {

    private final DefinedContributionService definedContributionService;

    @Autowired
    public DefinedContributionController(DefinedContributionService definedContributionService) {
        this.definedContributionService = definedContributionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDefinedContribution(@RequestBody DefinedContributionDTO definedContributionDto) {
        try {
            DefinedContribution createdDefinedContribution = definedContributionService.createDefinedContribution(definedContributionDto);
            return new ResponseEntity<>(createdDefinedContribution, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
