package com.pensionbackend.services;

import com.pensionbackend.dtos.ContributionDto;
import com.pensionbackend.entities.Contribution;
import com.pensionbackend.entities.Job;
import com.pensionbackend.entities.PensionPot;
import com.pensionbackend.entities.RequestApproval;
import com.pensionbackend.enums.ContributionFrequency;
import com.pensionbackend.repositories.ContributionRepository;
import com.pensionbackend.repositories.JobRepository;
import com.pensionbackend.repositories.PensionPotRepository;
import com.pensionbackend.repositories.RequestApprovalRepository;
import java.util.List;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributionServiceImpl implements ContributionService {

   private final ContributionRepository contributionRepository;
    private final RequestApprovalRepository requestApprovalRepository;
    private final JobRepository jobRepository;
    private final PensionPotRepository pensionPotRepository;

    @Autowired
    public ContributionServiceImpl(ContributionRepository contributionRepository,
                                   RequestApprovalRepository requestApprovalRepository,
                                   JobRepository jobRepository,
                                   PensionPotRepository pensionPotRepository) {
        this.contributionRepository = contributionRepository;
        this.requestApprovalRepository = requestApprovalRepository;
        this.jobRepository = jobRepository;
        this.pensionPotRepository = pensionPotRepository;
    }

    @Override
    public Contribution createContribution(Long userId, ContributionDto contributionDto) {
        Job userJob = jobRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User job details not found"));
    
        RequestApproval requestApproval = requestApprovalRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User job details not found"));
    
        if (!requestApproval.isApproved()) {
            throw new IllegalArgumentException("Request approval not yet approved");
        }
    
        double userSalary = userJob.getSalary();
        double contributionPercentage = contributionDto.getPercentage();
        double contributionAmount;

    if (contributionDto.getContributionFrequency() == ContributionFrequency.MONTHLY) {
        contributionAmount = (userSalary * contributionPercentage / 100.0) / 12.0;
    } else if (contributionDto.getContributionFrequency() == ContributionFrequency.QUARTERLY) {
        contributionAmount = (userSalary * contributionPercentage / 100.0) / 4.0;
    } else {
        contributionAmount = (userSalary * contributionPercentage / 100.0) / 12.0;
    }
        
    
        Contribution contribution = new Contribution();
        contribution.setUserId(userId);
        contribution.setPercentage(contributionDto.getPercentage());
        contribution.setAmount(contributionAmount);
        contribution.setContributionFrequency(contributionDto.getContributionFrequency());
        contribution.setStartDate(contributionDto.getStartDate());
        contribution.setEndDate(contributionDto.getEndDate());

         PensionPot pensionPot = new PensionPot();
    pensionPot.setUserId(userId);
    pensionPot.setTotalAmount(0);
    pensionPot.setCreationDate(new Date());
    pensionPot.setContribution(contribution);
    pensionPot.setName("Main Pension Pot");

    pensionPotRepository.save(pensionPot);
    
        return contributionRepository.save(contribution);
    }


    @Transactional
    public void deleteContributionByUserId(Long userId) {
        contributionRepository.deleteByUserId(userId);
    }


    @Override
public Contribution updateContribution(Long userId, ContributionDto contributionDto) {

    Optional<Contribution> optionalContribution = contributionRepository.findById(contributionDto.getId());
    if (optionalContribution.isPresent()) {

        Contribution existingContribution = optionalContribution.get();
        
        Job userJob = jobRepository.findByUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("User job details not found"));



     
        double userSalary = userJob.getSalary();
        double contributionPercentage = contributionDto.getPercentage();
        double contributionAmount;

        if (contributionDto.getContributionFrequency() == ContributionFrequency.MONTHLY) {
            contributionAmount = (userSalary * contributionPercentage / 100.0) / 12.0;
        } else if (contributionDto.getContributionFrequency() == ContributionFrequency.QUARTERLY) {
            contributionAmount = (userSalary * contributionPercentage / 100.0) / 4.0;
        } else {
            contributionAmount = (userSalary * contributionPercentage / 100.0) / 12.0;
        }

        existingContribution.setPercentage(contributionDto.getPercentage());
        existingContribution.setAmount(contributionAmount);
        existingContribution.setContributionFrequency(contributionDto.getContributionFrequency());
        existingContribution.setStartDate(contributionDto.getStartDate());
        existingContribution.setEndDate(contributionDto.getEndDate());

        return contributionRepository.save(existingContribution);
    } else {
        throw new IllegalArgumentException("Contribution with ID " + contributionDto.getId() + " not found.");
    }
}

@Override
public List<Contribution> getAllContributions() {
    return contributionRepository.findAll();
}

@Override
public Contribution getContributionByUserId(Long userId) {
    return contributionRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Contribution not found for user ID: " + userId));
}


}
