package com.pensionbackend.services;

import com.pensionbackend.entities.Compliance;
import com.pensionbackend.repositories.ComplianceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComplianceServiceImpl implements ComplianceService {

    private final ComplianceRepository complianceRepository;

    @Autowired
    public ComplianceServiceImpl(ComplianceRepository complianceRepository) {
        this.complianceRepository = complianceRepository;
    }

    @Override
 
    public void editComplianceResponse(Long complianceId, String response) {
        Compliance compliance = complianceRepository.findById(complianceId)
            .orElseThrow(() -> new IllegalArgumentException("Compliance not found with id: " + complianceId));
        
        compliance.setResponse(response);
        
        complianceRepository.save(compliance);
    }
}
