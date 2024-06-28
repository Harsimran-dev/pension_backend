package com.pensionbackend.services;

import java.util.List;

import com.pensionbackend.dtos.InvestmentDTO;
import com.pensionbackend.entities.Investment;

public interface InvestmentService {
    Investment updateInvestment(Long investmentId, double currentPrice);
    void transferProfitToPot(Long investmentId, Long pensionPotId);
    void getAllInvestmentsForUser(Long userId);
    List<Investment> getInvestments(Long userId);
}
