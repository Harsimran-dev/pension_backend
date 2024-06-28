package com.pensionbackend.dtos;

import com.pensionbackend.enums.ComplianceTrigger;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplianceDTO {
    private Long id;
    private Long userId;
    private String action;
    private ComplianceTrigger reason;
    private LocalDateTime notificationDate;
    private String emailContent;
    private boolean emailSent;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
