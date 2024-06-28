package com.pensionbackend.repositories;

import com.pensionbackend.entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    
    Optional<Contribution> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    
    
}
