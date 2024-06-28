package com.pensionbackend.services;

import com.pensionbackend.AddressNotFoundException;
import com.pensionbackend.dtos.AddressDto;
import com.pensionbackend.entities.Address;
import com.pensionbackend.entities.PensionUser;
import com.pensionbackend.repositories.AddressRepository;
import com.pensionbackend.repositories.PensionUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

   private final AddressRepository addressRepository;
    private final PensionUserRepository userRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, PensionUserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Address getAddressFromUser(Long user) {
        return addressRepository.findByUserId(user).orElse(null);
    }

    @Override
public Address addAddress(AddressDto addressDto, Long userId) {
    Address address = new Address();
    address.setLine1(addressDto.getLine1());
    address.setLine2(addressDto.getLine2());
    address.setCity(addressDto.getCity());
    address.setCounty(addressDto.getCounty());
    address.setPostalCode(addressDto.getPostalCode());
    address.setCountry(addressDto.getCountry());

    Optional<PensionUser> userOptional = userRepository.findById(userId);
    if (userOptional.isPresent()) {
      
        address.setUserId(userId);
    } else {
        throw new IllegalArgumentException("User not found with ID: " + userId);
    }

    return addressRepository.save(address);
}

@Override
public Address updateAddress(Long addressId, AddressDto addressDto) {
    Optional<Address> optionalAddress = addressRepository.findById(addressId);

    if (optionalAddress.isPresent()) {
        Address address = optionalAddress.get();
        address.setLine1(addressDto.getLine1());
        address.setLine2(addressDto.getLine2());
        address.setCity(addressDto.getCity());
        address.setCounty(addressDto.getCounty());
        address.setPostalCode(addressDto.getPostalCode());
        address.setCountry(addressDto.getCountry());
        return addressRepository.save(address);
    }

    throw new AddressNotFoundException(addressId);
}

@Override
public void deleteAddress(Long addressId) {
    if (!addressRepository.existsById(addressId)) {
        throw new AddressNotFoundException(addressId);
    }
    addressRepository.deleteById(addressId);
}



}
