package com.pensionbackend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import jakarta.persistence.Table;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pensionbackend.enums.ComplianceTrigger;


@Data
@Entity
@Table
public class Compliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne

    private PensionUser user;

    @OneToOne

    private Investment investment;



    private String Action;

    
    private ComplianceTrigger reason;


    private LocalDateTime notificationDate;

  
    private String emailContent;


    private boolean emailSent;


    private String response;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

}
