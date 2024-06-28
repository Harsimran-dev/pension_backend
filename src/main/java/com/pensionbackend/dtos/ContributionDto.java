package com.pensionbackend.dtos;

import com.pensionbackend.enums.ContributionFrequency;
import lombok.Data;

import java.util.Date;

@Data
public class ContributionDto {

    private Long id;

    private int percentage;

    private double amount;

    private Date startDate;

    private Date endDate;

    private Long userId;

    private ContributionFrequency contributionFrequency;

}
