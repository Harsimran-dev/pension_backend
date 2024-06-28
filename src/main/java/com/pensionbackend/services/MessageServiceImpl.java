package com.pensionbackend.services;

import com.pensionbackend.dtos.MessageDTO;
import com.pensionbackend.entities.Message;
import com.pensionbackend.entities.PensionUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.pensionbackend.repositories.MessageRepository;
import com.pensionbackend.repositories.PensionUserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private PensionUserRepository userRepository;
    private final EmailSendDoc emailSenddoc;
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    public MessageServiceImpl(EmailSendDoc emailSenddoc,MessageRepository messageRepository, PensionUserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.emailSenddoc=emailSenddoc;
    }
    @Override
    public Message addMessage(MessageDTO messageDto) {
        logger.info("Adding message: {}", messageDto);
        PensionUser user = userRepository.findById(messageDto.getUserId())
                                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + messageDto.getUserId()));
    
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setTimestamp(messageDto.getTimestamp());
        message.setSenderId(messageDto.getSenderId());
        message.setRecipientId(messageDto.getRecipientId());
        message.setUser(user);
    
        Message savedMessage = messageRepository.save(message);
        logger.info("Message added successfully: {}", savedMessage);
    
        if (messageDto.getSenderId() == 1) {
            Optional<PensionUser> recipientOptional = userRepository.findById(messageDto.getRecipientId());
            recipientOptional.ifPresent(recipient -> {
                String userEmail = recipient.getEmail();
                String subject = "New Message from Admin";
                String body = "Hi " + recipient.getFirstName() + ",\n\nAdmin has sent you a message\n"+ messageDto.getContent()+ "\n\nRegards,\nAdmin" ;
                emailSenddoc.sendEmail(userEmail, subject, body);
            });
        }
    
        return savedMessage;
    }
    
    
    

    

    @Override

    public ResponseEntity<List<Message>> getMessagesByUserId(Long userId) {
        List<Message> messages = messageRepository.findByUserId(userId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void deleteOldMessages() {
        LocalDateTime fifteenMinutesAgo = LocalDateTime.now().minusMinutes(15);
        
        List<Message> oldMessages = messageRepository.findByTimestampBefore(fifteenMinutesAgo);
        messageRepository.deleteAll(oldMessages);
    }
    

    
}
