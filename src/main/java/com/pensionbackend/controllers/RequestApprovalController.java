package com.pensionbackend.controllers;

import com.pensionbackend.entities.RequestApproval;
import com.pensionbackend.services.RequestApprovalService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/request-approval")
public class RequestApprovalController {

    private final RequestApprovalService requestApprovalService;

    public RequestApprovalController(RequestApprovalService requestApprovalService) {
        this.requestApprovalService = requestApprovalService;
    }

 
@GetMapping("/{userId}")
public ResponseEntity<Optional<RequestApproval>>getRequestApprovalByUserId(@PathVariable Long userId) {
    try {
       Optional< RequestApproval >requestApproval = requestApprovalService.getRequestApprovalByUserId(userId);
        return ResponseEntity.ok(requestApproval);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

}
