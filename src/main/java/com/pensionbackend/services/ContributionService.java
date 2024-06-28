package com.pensionbackend.services;

import java.util.List;

import com.pensionbackend.dtos.ContributionDto;
import com.pensionbackend.entities.Contribution;

public interface ContributionService {
    
    Contribution createContribution(Long userId, ContributionDto contributionDto);
    void deleteContributionByUserId(Long userId);
    Contribution updateContribution(Long userId, ContributionDto contributionDto);
    List<Contribution> getAllContributions();
    Contribution getContributionByUserId(Long userId);
    
}
