package com.pensionbackend.dtos;

import java.util.List;


import lombok.Data;

@Data
public class DefinedBenefitPensionSchemeDTO {
    private long id;
    private long userId;
    private Long company;
    private String employeeId;
    private double finalSalary;
    private int yearsOfService;
    private double accrualRate;
    private double netPensionSalary;
    private int retirementGoalAge;
    private List<BeneficiaryDTO> beneficiaries;
   
}
