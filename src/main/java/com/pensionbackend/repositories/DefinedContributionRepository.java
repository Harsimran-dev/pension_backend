package com.pensionbackend.repositories;

import com.pensionbackend.entities.DefinedContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefinedContributionRepository extends JpaRepository<DefinedContribution, Long> {
    DefinedContribution findByInvestmentOptionsId(Long investmentOptionsId);
    
}
