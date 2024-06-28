package com.pensionbackend.services;

import java.util.List;
import java.util.Optional;

import com.pensionbackend.dtos.DefinedBenefitPensionSchemeDTO;
import com.pensionbackend.entities.DefinedBenifitPensionScheme;

public interface DefinedBenifitService {
    DefinedBenifitPensionScheme findByUserId(Long userId);
       DefinedBenifitPensionScheme createDefinedPensionBenefitScheme(DefinedBenefitPensionSchemeDTO dto, double inflationRate, double taxRate,Long userId);
       void deleteDefinedPensionBenefitSchemeById(Long id);
       void updateDefinedPensionBenefitScheme(DefinedBenefitPensionSchemeDTO dto,double inflationRate, double taxRate,Long userId);
}
