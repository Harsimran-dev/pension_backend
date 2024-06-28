package com.pensionbackend.repositories;

import com.pensionbackend.entities.PensionPot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PensionPotRepository extends JpaRepository<PensionPot, Long> {
    
    List<PensionPot> findByUserId(Long userId);
    List<PensionPot> findByContributionIsNotNull();
    boolean existsByNameAndUserId(String name, Long userId);

    
}
