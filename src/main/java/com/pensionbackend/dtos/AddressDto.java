package com.pensionbackend.dtos;

import lombok.Data;

@Data
public class AddressDto {
    private String line1;
    private String line2;
    private String city;
    private String county;
    private String postalCode;
    private String country;
    private Long userId;
}
