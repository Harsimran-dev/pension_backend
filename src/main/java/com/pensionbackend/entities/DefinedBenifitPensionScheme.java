package com.pensionbackend.entities;


import com.pensionbackend.enums.DefinedBenifitCompanies;
import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Data
@Entity
public class DefinedBenifitPensionScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private Long userId;
    private Long company;
    private String employeeId;
    private double finalSalary;
    private int yearsOfService;
    private double accrualRate;
    private double netPensionSalary;
    private int retirementGoalAge;
    
}
