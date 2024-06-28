package com.pensionbackend.entities;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class PensionPot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    
   
private String name;

    private double totalAmount;

    private Date creationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Contribution contribution;
 

}

