package com.pensionbackend.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pensionbackend.entities.Companies;

public interface CompaniesRepository extends JpaRepository<Companies, Long>{
    
}
