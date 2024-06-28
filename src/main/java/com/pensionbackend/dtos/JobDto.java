package com.pensionbackend.dtos;

import lombok.Data;

import java.util.Date;



@Data
public class JobDto {
    private Long id;

    private String employeeId;
    private double salary;
    private String jobStarted;

    private String jobTitle;
 
    private Long companyId;
    private Long userId;
    private String randomUserNumber;

}
