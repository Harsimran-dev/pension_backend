package com.pensionbackend.repositories;

import com.pensionbackend.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserId(Long userId);
    List<Message> findByTimestampBefore(LocalDateTime timestamp);
}
