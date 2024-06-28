package com.pensionbackend.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pensionbackend.enums.Companies;

@Data
@Entity
public class DefinedContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date startDate;
    private Date endDate;
    private double minContribution;
    private double maxContribution;
    private double totalContributedAmount;
    private double currentContributedAmount;

    private String planStatus;
@ManyToOne
private PensionPot pensionPot;
  
@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
@JoinColumn(name = "investment_id")
private Investment investmentOptions ;


}
