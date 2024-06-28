package com.pensionbackend.controllers;

import com.pensionbackend.entities.Compliance;
import com.pensionbackend.repositories.ComplianceRepository;
import com.pensionbackend.services.ComplianceService;
import com.pensionbackend.services.InvestmentService;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import java.util.List;

@RestController
@RequestMapping("/api/v1/user/compliance")
public class ComplianceController {

    private final ComplianceService complianceService;
    private final ComplianceRepository complianceRepository;
     private final InvestmentService investmentService;

    @Autowired
    public ComplianceController(ComplianceService complianceService, ComplianceRepository complianceRepository,InvestmentService investmentService) {
        this.complianceService = complianceService;
        this.complianceRepository = complianceRepository;
        this.investmentService=investmentService;
    }

    @GetMapping("/{userId}")
    public List<Compliance> getComplianceForUser(@PathVariable Long userId) {
        investmentService.getAllInvestmentsForUser(userId);
        
        return complianceRepository.findByUserId(userId);
    }
    @PutMapping("/{complianceId}")
    public ResponseEntity<String> updateCompliance(@PathVariable Long complianceId, @RequestBody String response) {
        complianceService.editComplianceResponse(complianceId, response);
        return ResponseEntity.ok("Response updated successfully");
    }

   
    
    



    
}
