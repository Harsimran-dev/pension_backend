package com.pensionbackend.dtos;

import lombok.Data;

@Data
public class BeneficiaryDTO {
    private Long id;
    private String name;
    private String relationship;
}
