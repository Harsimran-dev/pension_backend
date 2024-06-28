package com.pensionbackend.repositories;

import com.pensionbackend.entities.FundTransferHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundTransferHistoryRepository extends JpaRepository<FundTransferHistory, Long> {
    List<FundTransferHistory> findByUserId(Long userId);
}
