package com.pensionbackend.services;

import java.util.Optional;

import com.pensionbackend.entities.RequestApproval;

public interface RequestApprovalService {

     RequestApproval updateRequestApproval(String randomUserNumber, boolean approved);
     Optional<RequestApproval> getRequestApprovalByUserId(Long userId);
     
    
}
