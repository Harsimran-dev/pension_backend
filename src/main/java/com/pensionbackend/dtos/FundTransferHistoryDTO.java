package com.pensionbackend.dtos;

import java.sql.Date;

import lombok.Data;

@Data
public class FundTransferHistoryDTO {
    
    private String fromAccount;
    
    private String toAccount;
    
    private double amount;
    private Long userId;
     private Date creationDate; 
  

  
}
