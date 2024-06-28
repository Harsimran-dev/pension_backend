package com.pensionbackend.controllers;

import com.pensionbackend.entities.PensionPot;
import com.pensionbackend.repositories.PensionPotRepository;
import com.pensionbackend.services.PensionPotUpdate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/pension-pot")
public class PensionPotController {

  private final PensionPotUpdate pensionPotUpdate;
private final PensionPotRepository pensionPotRepository;

@Autowired
public PensionPotController(PensionPotUpdate pensionPotUpdate, PensionPotRepository pensionPotRepository) {
    this.pensionPotUpdate = pensionPotUpdate;
    this.pensionPotRepository = pensionPotRepository;
}

    @PostMapping("/create")
    public ResponseEntity<?> createPensionPot(@RequestBody PensionPot pensionPotDto) {
        try {
            Long userId = pensionPotDto.getUserId();
            List<PensionPot> existingPots = pensionPotRepository.findByUserId(userId);
            if (!existingPots.isEmpty()) {
                pensionPotUpdate.createPot(pensionPotDto);
                return ResponseEntity.status(HttpStatus.CREATED).body("Pension pot created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please start contributing");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    
   @PutMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam double amount) {
        try {
            pensionPotUpdate.transferFunds(senderId, receiverId, amount);
            return ResponseEntity.ok().body("{'message': 'Funds transferred successfully.'}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePensionPotById(@PathVariable Long id) {
        Optional<PensionPot> optionalPensionPot = pensionPotRepository.findById(id);
        if (optionalPensionPot.isPresent()) {
            PensionPot pensionPot = optionalPensionPot.get();
            if (pensionPot.getTotalAmount() > 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete. Please transfer money first or create a new pot.");
            } else {
                try {
                    pensionPotRepository.deleteById(id);
                    return ResponseEntity.ok("Pension pot deleted successfully.");
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pension pot not found.");
        }
    }

    @GetMapping("/user/{userId}")
public ResponseEntity<?> getPensionPotsByUserId(@PathVariable Long userId) {
    try {
        List<PensionPot> pensionPots = pensionPotUpdate.getPensionPotsByUserId(userId);
        return ResponseEntity.ok(pensionPots);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

@GetMapping("/{id}")
public ResponseEntity<?> getPensionPotById(@PathVariable Long id) {
    try {
        Optional<PensionPot> pensionPot = pensionPotUpdate.getPensionPotById(id);
        if (pensionPot.isPresent()) {
            return ResponseEntity.ok(pensionPot.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pension pot not found.");
        }
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

    
}
