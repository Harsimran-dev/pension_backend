package com.pensionbackend.controllers;

import com.pensionbackend.dtos.InvestmentDTO;
import com.pensionbackend.entities.Investment;
import com.pensionbackend.services.InvestmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    @Autowired
    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInvestment(@RequestParam("investmentId") Long investmentId, 
                                               @RequestParam("currentPrice") Double currentPrice) {
        try {
            Investment updatedInvestment = investmentService.updateInvestment(investmentId, currentPrice);
            return new ResponseEntity<>(updatedInvestment, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

    @PutMapping("/transfer-profit-to-pot/{investmentId}/{pensionPotId}")
    public ResponseEntity<?> transferProfitToPot(
            @PathVariable("investmentId") Long investmentId,
            @PathVariable("pensionPotId") Long pensionPotId) {
        try {
            investmentService.transferProfitToPot(investmentId, pensionPotId);
            return ResponseEntity.ok("Profit transferred successfully to the pension pot.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllInvestmentsForUser(@PathVariable Long userId) {
        try {
            investmentService.getAllInvestmentsForUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{userId}/investments")
public ResponseEntity<List<Investment>> getInvestmentsForUser(@PathVariable Long userId) {
    try {
        List<Investment> investments = investmentService.getInvestments(userId);
        return ResponseEntity.ok(investments);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    
    
}
