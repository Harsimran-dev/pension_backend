package com.pensionbackend.dtos;

import lombok.Data;


import java.util.Date;
import java.util.List;

import com.pensionbackend.enums.Companies;

@Data
public class DefinedContributionDTO {
    private String name;

    private Date startDate;
    private Date endDate;
    private double minContribution;
    private double maxContribution;
    private double totalContributedAmount;
    private String planStatus;
    private Long pensionPotId;
    private double currentContributedAmount;
    private InvestmentDTO investmentOptions;


    public double getCurrentContributedAmount() {
        return currentContributedAmount;
    }

    public void setCurrentContributedAmount(double currentContributedAmount) {
        this.currentContributedAmount = currentContributedAmount;
    }
}
