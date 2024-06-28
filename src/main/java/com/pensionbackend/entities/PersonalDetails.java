package com.pensionbackend.entities;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


import java.util.Date;

@Data
@Entity
@Table(name = "personal_details")
public class PersonalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "IMG", length = 10000000)
    private byte[] profilePhoto;
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String nationality;

    private String education;
    private String niNumber;


    private Long userId;

}
