package com.pensionbackend.dtos;

import com.pensionbackend.enums.InvestmentResult;
import com.pensionbackend.enums.InvestmentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class InvestmentDTO {

    private Long id;

    private InvestmentType investmentType;

    private String symbol;

    private String name;

    private double quantity;

    private double purchasePrice;

    private Date purchaseDate;

    private double currentPrice;
    private Long pensionPotId;

    private double marketValue;

    private Date investmentDate;

    private String investmentStatus;

    private Long userId;
    @Enumerated(EnumType.STRING)
private InvestmentResult investmentResult;


    private double investmentReturn;
}
