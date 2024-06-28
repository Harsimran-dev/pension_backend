package com.pensionbackend.services;

import com.pensionbackend.entities.FundTransferHistory;
import com.pensionbackend.entities.PensionPot;
import com.pensionbackend.repositories.FundTransferHistoryRepository;
import com.pensionbackend.repositories.PensionPotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class PensionPotUpdate {

    private final PensionPotRepository pensionPotRepository;
    private final FundTransferHistoryRepository fundTransferRepository;

    @Autowired
    public PensionPotUpdate(PensionPotRepository pensionPotRepository, FundTransferHistoryRepository fundTransferRepository) {
        this.pensionPotRepository = pensionPotRepository;
        this.fundTransferRepository = fundTransferRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void updatePensionPotEvery1Minute() {
        List<PensionPot> pensionPots = pensionPotRepository.findByContributionIsNotNull();
        for (PensionPot pensionPot : pensionPots) {
            double totalAmount = pensionPot.getTotalAmount();
            double contributionAmount = pensionPot.getContribution().getAmount();
            double newTotalAmount = totalAmount + contributionAmount;
            pensionPot.setTotalAmount(newTotalAmount);
            pensionPotRepository.save(pensionPot);
        }
    }
    
     
  
    public void createPot(PensionPot pensionPotDto) {
        String name = pensionPotDto.getName();
        if (name != null && pensionPotRepository.existsByNameAndUserId(name,pensionPotDto.getUserId())) {
            throw new RuntimeException("Pension pot with name " + name + " already exists.");
        }
        
        Long userId = pensionPotDto.getUserId();
        
        PensionPot newPensionPot = new PensionPot();
        newPensionPot.setUserId(userId);
        newPensionPot.setTotalAmount(0);
        newPensionPot.setCreationDate(pensionPotDto.getCreationDate());
        newPensionPot.setName(name);
        
        pensionPotRepository.save(newPensionPot);
    }
    


    public void transferFunds(Long fromId, Long toId, double amount) {
        Optional<PensionPot> fromPotOptional = pensionPotRepository.findById(fromId);
        Optional<PensionPot> toPotOptional = pensionPotRepository.findById(toId);
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
    
        if (fromPotOptional.isEmpty() || toPotOptional.isEmpty()) {
            throw new IllegalArgumentException("Sender or receiver pension pot not found.");
        }
    
        PensionPot fromPot = fromPotOptional.get();
        PensionPot toPot = toPotOptional.get();
    
        if (fromPot.getTotalAmount() < amount) {
            throw new IllegalArgumentException("Insufficient funds in sender pension pot.");
        }
    
        double remainingAmount = fromPot.getTotalAmount() - amount;
        fromPot.setTotalAmount(remainingAmount);
        toPot.setTotalAmount(toPot.getTotalAmount() + amount);
    
        pensionPotRepository.save(fromPot);
        pensionPotRepository.save(toPot);

          FundTransferHistory fundTransferHistory = new FundTransferHistory();
    fundTransferHistory.setFromAccount(fromPot.getName());
    fundTransferHistory.setToAccount(toPot.getName());
    fundTransferHistory.setAmount(amount);
    fundTransferHistory.setUserId(toPot.getUserId());
    fundTransferHistory.setCreationDate(new java.sql.Date(new Date().getTime()));

    fundTransferRepository.save(fundTransferHistory);
    }
    

    public List<PensionPot> getPensionPotsByUserId(Long userId) {
        return pensionPotRepository.findByUserId(userId);
    }

    public Optional<PensionPot> getPensionPotById(Long id) {
        return pensionPotRepository.findById(id);
    }
    
    
    
}
