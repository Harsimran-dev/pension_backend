package com.pensionbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pensionbackend.entities.Investment;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.IOException;

@Service
public class EmailSendDocImpl implements EmailSendDoc {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendPensionFile(String toEmail, String subject, MultipartFile[] files) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText("Hi Thanks for registering to Pension Plan , You can see the benifits below");

            for (MultipartFile file : files) {
                try {
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(file.getBytes(), file.getContentType());
                    helper.addAttachment(file.getOriginalFilename(), dataSource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);
    
    
    
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    

    @Override
public void sendEmailWithTrigger(String toEmail, String subject, Investment investment, String message, String trigger) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        
        String emailBody = "Dear Sir/Madam,\n\n" +
        "We would like to inform you that your investment named '" + investment.getName() + "' has triggered the following event:\n" +
        "\n" +
        "Event: " + message + "\n" +
        "Trigger: " + trigger + "\n\n" +
        "As a responsible investor, we recommend studying the current market trends and company performance indicators.\n" +
        "Thank you for your attention.\n\n" +
        "Sincerely,\n" +
        "Your Pension Plan Management Team";


        helper.setText(emailBody);

        javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}

}
