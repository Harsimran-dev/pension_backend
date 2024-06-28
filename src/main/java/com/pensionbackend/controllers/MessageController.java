package com.pensionbackend.controllers;

import com.pensionbackend.dtos.MessageDTO;
import com.pensionbackend.entities.Message;
import com.pensionbackend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/message")
public class MessageController {

    private final MessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addMessage(@RequestBody MessageDTO messageDto) {
        logger.info("Received request to add message: {}", messageDto);
        try{
            Message message = messageService.addMessage(messageDto);
               return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
       
       
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Long userId) {
        return messageService.getMessagesByUserId(userId);
    }
}
