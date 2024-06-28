package com.pensionbackend.services;

import com.pensionbackend.dtos.MessageDTO;
import com.pensionbackend.entities.Message;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface MessageService {
    Message addMessage(MessageDTO messageDto);
    ResponseEntity<List<Message>> getMessagesByUserId(Long userId);
}
