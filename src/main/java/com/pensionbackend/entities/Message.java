package com.pensionbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String content;
    
    private LocalDateTime timestamp;
    

    private Long senderId;
    private Long recipientId;

  

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PensionUser user;

}
