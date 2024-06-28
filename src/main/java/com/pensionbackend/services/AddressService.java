package com.pensionbackend.services;

import com.pensionbackend.dtos.AddressDto;
import com.pensionbackend.entities.Address;


public interface AddressService {
    Address getAddressFromUser(Long userId);
    Address addAddress(AddressDto addressDto, Long userId);
    Address updateAddress(Long addressId, AddressDto addressDto);
    void deleteAddress(Long addressId);
}
