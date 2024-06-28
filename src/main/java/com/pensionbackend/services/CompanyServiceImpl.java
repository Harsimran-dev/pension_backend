package com.pensionbackend.services;

import com.pensionbackend.dtos.CompaniesDTO;
import com.pensionbackend.entities.Companies;
import com.pensionbackend.repositories.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompaniesRepository companiesRepository;

    @Autowired
    public CompanyServiceImpl(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    @Override
    public ResponseEntity<String> createCompany(CompaniesDTO companyDto) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        
        try {
            logger.info("Received request to create company with DTO: {}", companyDto);
            
            Companies company = new Companies();
            company.setName(companyDto.getName());
            company.setType(companyDto.getType());
            
            logger.info("Creating company entity: {}", company);
            
            Companies createdCompany = companiesRepository.save(company);
            
            logger.info("Company created successfully with ID: {}", createdCompany.getId());
            
            return new ResponseEntity<>("Company created successfully with ID: " + createdCompany.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create company: {}", e.getMessage());
            return new ResponseEntity<>("Failed to create company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
}
