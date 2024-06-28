package com.pensionbackend.entities;

import com.pensionbackend.enums.InvestmentResult;
import com.pensionbackend.enums.InvestmentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InvestmentType investmentType;

    private String symbol;

    private String name;

    private double quantity;

    private double purchasePrice;

    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    private double currentPrice;

    private double marketValue;

    @Temporal(TemporalType.DATE)
    private Date investmentDate;

    private String investmentStatus;
    @Enumerated(EnumType.STRING)
private InvestmentResult investmentResult;
@ManyToOne
private PensionPot pensionPot;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private PensionUser user;

    private double investmentReturn;

    public void setDefinedContribution(DefinedContribution definedContribution) {
    }
}
