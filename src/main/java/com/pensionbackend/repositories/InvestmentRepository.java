package com.pensionbackend.repositories;

import com.pensionbackend.entities.Investment;
import com.pensionbackend.entities.PensionUser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUser(PensionUser user);
    List<Investment> findByUserId(Long userId);
}

