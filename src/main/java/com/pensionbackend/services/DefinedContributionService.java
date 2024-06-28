package com.pensionbackend.services;


import com.pensionbackend.dtos.DefinedContributionDTO;
import com.pensionbackend.entities.DefinedContribution;

public interface DefinedContributionService {
    
    DefinedContribution createDefinedContribution(DefinedContributionDTO definedContributionDto);
    
    
    
    
}
