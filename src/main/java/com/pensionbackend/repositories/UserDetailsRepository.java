package com.pensionbackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pensionbackend.entities.PersonalDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<PersonalDetails, Long> {
    
    Optional<PersonalDetails> findByUserId(Long userId);

}
