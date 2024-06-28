package com.pensionbackend.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import com.pensionbackend.enums.Companies;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private double salary;
    private String jobStarted;
   
    private String jobTitle;

    private Long companyId;
    private Long userId;
    

 
}
