package com.pensionbackend.services;


import com.pensionbackend.dtos.DefinedContributionDTO;
import com.pensionbackend.dtos.InvestmentDTO;
import com.pensionbackend.entities.DefinedContribution;
import com.pensionbackend.entities.PensionPot;
import com.pensionbackend.entities.PensionUser;
import com.pensionbackend.enums.Companies;
import com.pensionbackend.enums.InvestmentType;
import com.pensionbackend.entities.Investment;
import com.pensionbackend.repositories.DefinedContributionRepository;
import com.pensionbackend.repositories.PensionPotRepository;
import com.pensionbackend.repositories.PensionUserRepository;

import jakarta.persistence.EntityNotFoundException;

import com.pensionbackend.repositories.InvestmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DefinedContributionServiceImpl implements DefinedContributionService {

private final DefinedContributionRepository definedContributionRepository;
private final PensionPotRepository pensionPotRepository;
private final InvestmentRepository investmentRepository;
private final PensionUserRepository pensionUserRepository; 

@Autowired
public DefinedContributionServiceImpl(DefinedContributionRepository definedContributionRepository,
                                      PensionPotRepository pensionPotRepository,
                                      InvestmentRepository investmentRepository,
                                      PensionUserRepository pensionUserRepository) { 
    this.definedContributionRepository = definedContributionRepository;
    this.pensionPotRepository = pensionPotRepository;
    this.investmentRepository = investmentRepository;
    this.pensionUserRepository = pensionUserRepository; 
}

@Override
@Transactional
public DefinedContribution createDefinedContribution(DefinedContributionDTO definedContributionDto) {
    DefinedContribution definedContribution = new DefinedContribution();
    definedContribution.setName(definedContributionDto.getName());

    definedContribution.setStartDate(definedContributionDto.getStartDate());
    definedContribution.setEndDate(definedContributionDto.getEndDate());
    definedContribution.setMinContribution(definedContributionDto.getMinContribution());
    definedContribution.setMaxContribution(definedContributionDto.getMaxContribution());
    definedContribution.setTotalContributedAmount((definedContributionDto.getTotalContributedAmount())+definedContributionDto.getCurrentContributedAmount());
    definedContribution.setPlanStatus(definedContributionDto.getPlanStatus());

    Optional<PensionPot> optionalPensionPot = pensionPotRepository.findById(definedContributionDto.getPensionPotId());
    PensionPot pensionPot = optionalPensionPot.orElseThrow(() -> new EntityNotFoundException("PensionPot not found"));
    Long userId = pensionPot.getUserId();
    Optional<PensionUser> optionalPensionUser = pensionUserRepository.findById(userId);
    PensionUser pensionUser = optionalPensionUser.orElseThrow(() -> new EntityNotFoundException("PensionUser not found"));

    double currentPotAmount = pensionPot.getTotalAmount();
    double contributedAmount = definedContributionDto.getCurrentContributedAmount();
    if (contributedAmount > currentPotAmount) {
        throw new IllegalArgumentException("Contributed amount exceeds available funds in the pension pot.");
    }

    pensionPot.setTotalAmount(currentPotAmount - contributedAmount);

    definedContribution.setPensionPot(pensionPot);
    
    InvestmentDTO investmentDto = definedContributionDto.getInvestmentOptions();
    if (investmentDto != null) {
        Investment investment = new Investment();
        investment.setDefinedContribution(definedContribution);
        investment.setInvestmentType(investmentDto.getInvestmentType());
        investment.setSymbol(investmentDto.getSymbol());
        investment.setName(investmentDto.getName());
        investment.setUser(pensionUser);
       investment.setPensionPot(pensionPot);
        investment.setQuantity(investmentDto.getQuantity());
        investment.setPurchasePrice(investmentDto.getPurchasePrice());
        investment.setPurchaseDate(new Date());
        investment.setCurrentPrice(investmentDto.getCurrentPrice());
        investment.setMarketValue(investmentDto.getMarketValue());
        investment.setInvestmentDate(new Date());
        investment.setInvestmentStatus(investmentDto.getInvestmentStatus());
        investmentRepository.save(investment);
        
        definedContribution.setInvestmentOptions(investment);
    }

    return definedContributionRepository.save(definedContribution);
}


}
