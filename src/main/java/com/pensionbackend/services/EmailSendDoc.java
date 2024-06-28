package com.pensionbackend.services;

import org.springframework.web.multipart.MultipartFile;

import com.pensionbackend.entities.Investment;

public interface EmailSendDoc {
    void sendPensionFile(String toEmail, String subject, MultipartFile[] files);
    void sendEmail(String toEmail, String subject, String body);
     void sendEmailWithTrigger(String toEmail, String subject, Investment investment, String message, String trigger);
}
