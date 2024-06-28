package com.pensionbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pensionbackend.entities.RequestApproval;

public interface RequestApprovalRepository extends JpaRepository<RequestApproval, Long> {
    Optional<RequestApproval> findByUserId(Long userId);

    Optional<RequestApproval> findByUserIdAndApproved(Long userId, boolean approved);
    Optional<RequestApproval> findByRandomUserNumber(String randomUserNumber);
    Optional<RequestApproval> findByRandomUserNumberAndApproved(String randomUserNumber, boolean approved);

}
