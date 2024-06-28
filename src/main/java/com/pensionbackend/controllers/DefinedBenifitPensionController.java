package com.pensionbackend.controllers;

import com.pensionbackend.dtos.DefinedBenefitPensionSchemeDTO;
import com.pensionbackend.entities.DefinedBenifitPensionScheme;
import com.pensionbackend.services.DefinedBenifitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/defined-benefit-pension")
public class DefinedBenifitPensionController {

    private final DefinedBenifitService pensionService;

    @Autowired
    public DefinedBenifitPensionController(DefinedBenifitService pensionService) {
        this.pensionService = pensionService;
    }

    @PostMapping("/create")
    public DefinedBenifitPensionScheme createDefinedPensionBenefitScheme(@RequestBody DefinedBenefitPensionSchemeDTO dto,
                                                                         @RequestParam double inflationRate,
                                                                         @RequestParam double taxRate,
                                                                         @RequestParam Long userId) {
        return pensionService.createDefinedPensionBenefitScheme(dto, inflationRate, taxRate, userId);
    }
    

@GetMapping("/user/{userId}")
public ResponseEntity<DefinedBenifitPensionScheme>findByUserId(@PathVariable Long userId) {
    DefinedBenifitPensionScheme schemes = pensionService.findByUserId(userId);
    if (schemes==null) {
        return ResponseEntity.notFound().build();
    } else {
        return ResponseEntity.ok(schemes);
    }
}
    @DeleteMapping("/{id}")
public void deleteDefinedPensionBenefitSchemeById(@PathVariable Long id) {
    pensionService.deleteDefinedPensionBenefitSchemeById(id);
}

@PutMapping("/update")
public void updateDefinedPensionBenefitScheme(@RequestBody DefinedBenefitPensionSchemeDTO dto,
                                               @RequestParam double inflationRate,
                                               @RequestParam double taxRate,
                                               @RequestParam Long userId) {
    pensionService.updateDefinedPensionBenefitScheme(dto, inflationRate, taxRate, userId);
}

}
