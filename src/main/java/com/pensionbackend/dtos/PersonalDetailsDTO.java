package com.pensionbackend.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class PersonalDetailsDTO {

    private Long id;
    private Long userId;
    private byte[] profilePhoto;
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String nationality;

    private String education;
    private String niNumber;


    public PersonalDetailsDTO(Long id, Long userId,  byte[] profilePhoto, String fullName, Date dateOfBirth,
            String gender, String nationality, String address, String education, String niNumber,
            String niCategory) {
        this.id = id;
        this.userId = userId;
        this.profilePhoto = profilePhoto;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
 
        this.education = education;
        this.niNumber = niNumber;

    }

}
