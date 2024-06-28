package com.pensionbackend.controllers;

import com.pensionbackend.entities.Companies;
import com.pensionbackend.repositories.CompaniesRepository;
import com.pensionbackend.services.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/companies")
public class CompaniesController {

    private final CompaniesRepository companiesRepository;
    private final JobService jobService;

    @Autowired
    public CompaniesController(CompaniesRepository companiesRepository, JobService jobService) {
        this.companiesRepository = companiesRepository;
        this.jobService = jobService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Companies>> getAllCompanies() {
        List<Companies> companies = companiesRepository.findAll();
        if (companies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Companies> getCompanyById(@PathVariable Long id) {
        Optional<Companies> companyOptional = companiesRepository.findById(id);
        if (companyOptional.isPresent()) {
            return new ResponseEntity<>(companyOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/companyType/{userId}")
public ResponseEntity<String> getCompanyTypeByUserId(@PathVariable Long userId) {
    String companyType = jobService.getCompanyTypeByUserId(userId);
    if (companyType.equals("Company not found") || companyType.equals("Job not found for the given user ID")) {
        return new ResponseEntity<>(companyType, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(companyType, HttpStatus.OK);
}
}
