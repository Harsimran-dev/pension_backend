package com.pensionbackend.controllers;

import com.pensionbackend.AddressNotFoundException;
import com.pensionbackend.dtos.AddressDto;
import com.pensionbackend.entities.Address;
import com.pensionbackend.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Address> getAddressFromUser(@PathVariable Long userId) {
        Address address = addressService.getAddressFromUser(userId);
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Address> createAddress(@RequestBody AddressDto addressDto, @PathVariable Long userId) {
        Address createdAddress = addressService.addAddress(addressDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody AddressDto addressDto) {
        Address updatedAddress = addressService.updateAddress(addressId, addressDto);
        if (updatedAddress == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.noContent().build();
        } catch (AddressNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
