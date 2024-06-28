package com.pensionbackend.repositories;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import com.pensionbackend.entities.Compliance;
import com.pensionbackend.enums.ComplianceTrigger;

public interface ComplianceRepository extends JpaRepository<Compliance, Long> {
  Compliance findByInvestmentIdAndReason(Long investmentId, ComplianceTrigger reason);
   Compliance findByInvestmentId(Long investmentId);
    List<Compliance> findByUserId(Long userId);

    void deleteByInvestmentId(Long investmentId);
}
