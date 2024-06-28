package com.pensionbackend.entities;



import com.pensionbackend.enums.Companies;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.*;


import lombok.Data;

@Data
@Entity
public class RequestApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Companies companyName;

    private String randomUserNumber;

    private boolean approved;

  
}
