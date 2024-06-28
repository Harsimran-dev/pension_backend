package com.pensionbackend.dtos;

import java.util.Date;
import lombok.Data;

@Data
public class PensionPotDTO {
    private Long id;
    private Long userId;
    private double totalAmount;
    private Date creationDate;
    private Long contributionId;
    private String name; 
}
