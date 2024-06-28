package com.pensionbackend.controllers;

import java.util.List;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.pensionbackend.dtos.CompaniesDTO;
import com.pensionbackend.dtos.MessageDTO;
import com.pensionbackend.entities.Companies;
import com.pensionbackend.entities.Compliance;
import com.pensionbackend.entities.DefinedBenifitPensionScheme;
import com.pensionbackend.entities.Investment;
import com.pensionbackend.entities.Message;
import com.pensionbackend.entities.PensionPot;
import com.pensionbackend.entities.PensionUser;
import com.pensionbackend.repositories.CompaniesRepository;
import com.pensionbackend.repositories.ComplianceRepository;
import com.pensionbackend.repositories.PensionUserRepository;
import com.pensionbackend.services.AuthService;
import com.pensionbackend.services.CompanyService;
import com.pensionbackend.services.DefinedBenifitService;
import com.pensionbackend.services.InvestmentService;
import com.pensionbackend.services.JobService;
import com.pensionbackend.services.MessageService;
import com.pensionbackend.services.PensionPotUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AuthService authService;
    private final ComplianceRepository complianceRepository;
    private final JobService jobService;
    private final InvestmentService investmentService;
    private final PensionPotUpdate pensionPotUpdate;
    private final DefinedBenifitService pensionService;
       private final PensionUserRepository userRepository;
         private final CompaniesRepository companiesRepository;
             private final CompanyService companyService;
               private final MessageService messageService;

    @Autowired
    public AdminController(MessageService messageService,CompanyService companyService,CompaniesRepository companiesRepository,AuthService authService, PensionUserRepository userRepository, ComplianceRepository complianceRepository, JobService jobService, InvestmentService investmentService, PensionPotUpdate pensionPotUpdate, DefinedBenifitService pensionService) {
        this.authService = authService;
        this.complianceRepository = complianceRepository;
        this.jobService = jobService;
        this.investmentService = investmentService;
        this.pensionPotUpdate = pensionPotUpdate;
        this.pensionService = pensionService;
        this.companyService = companyService;
        this.userRepository=userRepository;
        this.companiesRepository = companiesRepository;
        this.messageService = messageService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<PensionUser>> getAllUsers() {
        List<PensionUser> users = authService.listAllUsers();
        return ResponseEntity.ok(users);
    }

      @GetMapping("/{userId}")
    public List<Compliance> getComplianceForUser(@PathVariable Long userId) {
        return complianceRepository.findByUserId(userId);
    }
        @GetMapping("/companyType/{userId}")
public ResponseEntity<String> getCompanyTypeByUserId(@PathVariable Long userId) {
    String companyType = jobService.getCompanyTypeByUserId(userId);
    if (companyType.equals("Company not found") || companyType.equals("Job not found for the given user ID")) {
        return new ResponseEntity<>(companyType, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(companyType, HttpStatus.OK);
}

 @GetMapping("/user/{userId}/investments")
public ResponseEntity<List<Investment>> getInvestmentsForUser(@PathVariable Long userId) {
    try {
        List<Investment> investments = investmentService.getInvestments(userId);
        return ResponseEntity.ok(investments);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
    @GetMapping("/pensionpot/{userId}")
public ResponseEntity<?> getPensionPotsByUserId(@PathVariable Long userId) {
    try {
        List<PensionPot> pensionPots = pensionPotUpdate.getPensionPotsByUserId(userId);
        return ResponseEntity.ok(pensionPots);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
  @GetMapping("/user/{userId}")
    public DefinedBenifitPensionScheme findByUserId(@PathVariable Long userId) {
        return pensionService.findByUserId(userId);
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId) {
        try {
            authService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }

    @PostMapping("/company/create/{name}/{type}")
    public ResponseEntity<String> createCompany(@PathVariable String name, @PathVariable String type) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
    
        try {
            logger.info("Received request to create company with name: {} and type: {}", name, type);
            
            CompaniesDTO companyDto = new CompaniesDTO();
            companyDto.setName(name);
            companyDto.setType(type);
    
            return companyService.createCompany(companyDto);
        } catch (Exception e) {
            logger.error("Failed to create company: {}", e.getMessage());
            return new ResponseEntity<>("Failed to create company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }


    
  


}
