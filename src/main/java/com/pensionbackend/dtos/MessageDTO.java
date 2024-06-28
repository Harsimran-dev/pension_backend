package com.pensionbackend.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {

    private String content;

    private LocalDateTime timestamp;

  

    private Long senderId;

    private Long recipientId;
    
    private Long userId;

}
