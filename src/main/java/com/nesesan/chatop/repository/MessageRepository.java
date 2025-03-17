package com.nesesan.chatop.repository;

import com.nesesan.chatop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository <Message, Integer>{
}
