package com.pensionbackend.services;

import com.pensionbackend.entities.RequestApproval;
import com.pensionbackend.repositories.RequestApprovalRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestApprovalServiceImpl implements RequestApprovalService {

    private final RequestApprovalRepository requestApprovalRepository;

    @Autowired
    public RequestApprovalServiceImpl(RequestApprovalRepository requestApprovalRepository) {
        this.requestApprovalRepository = requestApprovalRepository;
    }

    @Override
    public RequestApproval updateRequestApproval(String randomUserNumber, boolean approved) {
        Optional<RequestApproval> requestApprovalOptional = requestApprovalRepository.findByRandomUserNumber(randomUserNumber);
        if (requestApprovalOptional.isPresent()) {
            RequestApproval requestApproval = requestApprovalOptional.get();
            requestApproval.setApproved(approved);
            return requestApprovalRepository.save(requestApproval);
        } else {
            throw new IllegalArgumentException("Request approval not found for random user number: " + randomUserNumber);
        }
    }

    @Override
    public Optional<RequestApproval> getRequestApprovalByUserId(Long userId) {
        return requestApprovalRepository.findByUserId(userId);
    }
}
