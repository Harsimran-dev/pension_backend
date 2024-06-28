package com.pensionbackend.services;

import com.pensionbackend.dtos.CompaniesDTO;
import com.pensionbackend.entities.Companies;
import org.springframework.http.ResponseEntity;

public interface CompanyService {
    ResponseEntity<String> createCompany(CompaniesDTO companyDto);
}
