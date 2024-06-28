package com.pensionbackend.controllers;

import com.pensionbackend.dtos.ContributionDto;
import com.pensionbackend.entities.Contribution;
import com.pensionbackend.services.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/contribution")
public class ContributionController {

    private final ContributionService contributionService;

    @Autowired
    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping("{userId}")
    public ResponseEntity<?> createContribution(@PathVariable Long userId, @RequestBody ContributionDto contributionDto) {
        try {
            Contribution contribution = contributionService.createContribution(userId, contributionDto);
            return new ResponseEntity<>(contribution, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


     @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteContributionByUserId(@PathVariable Long userId) {
        try {
            contributionService.deleteContributionByUserId(userId);
            return ResponseEntity.ok("Contributions deleted for user with ID: " + userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

     @PutMapping("/{userId}")
    public ResponseEntity<?> updateContribution(@PathVariable Long userId, @RequestBody ContributionDto contributionDto) {
        try {
            Contribution updatedContribution = contributionService.updateContribution(userId, contributionDto);
            return ResponseEntity.ok(updatedContribution);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
public ResponseEntity<?> getContributionByUserId(@PathVariable Long userId) {
    try {
        Contribution contribution = contributionService.getContributionByUserId(userId);
        return ResponseEntity.ok(contribution);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}




}
