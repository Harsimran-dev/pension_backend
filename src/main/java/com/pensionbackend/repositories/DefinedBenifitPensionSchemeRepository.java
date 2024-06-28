package com.pensionbackend.repositories;

import com.pensionbackend.entities.DefinedBenifitPensionScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DefinedBenifitPensionSchemeRepository extends JpaRepository<DefinedBenifitPensionScheme, Long> {
    DefinedBenifitPensionScheme findByUserId(Long userId);
}
