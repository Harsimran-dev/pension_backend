
package com.pensionbackend.controllers;
import com.pensionbackend.entities.FundTransferHistory;
import com.pensionbackend.repositories.FundTransferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/fund-transfer-history")
public class FundTransferHistoryController {

    private final FundTransferHistoryRepository fundTransferHistoryRepository;

    @Autowired
    public FundTransferHistoryController(FundTransferHistoryRepository fundTransferHistoryRepository) {
        this.fundTransferHistoryRepository = fundTransferHistoryRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FundTransferHistory>> getFundTransferHistoryByUserId(@PathVariable Long userId) {
        List<FundTransferHistory> fundTransferHistoryList = fundTransferHistoryRepository.findByUserId(userId);
        return ResponseEntity.ok(fundTransferHistoryList);
    }
}
