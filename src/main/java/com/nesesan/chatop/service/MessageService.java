package com.nesesan.chatop.service;

import com.nesesan.chatop.Exceptions.ResourceNotFoundException;
import com.nesesan.chatop.dto.message.MessageDTO;
import com.nesesan.chatop.dto.message.MessageResponseDTO;
import com.nesesan.chatop.model.Message;
import com.nesesan.chatop.model.Rental;
import com.nesesan.chatop.model.User;
import com.nesesan.chatop.repository.MessageRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final RentalService rentalService;

    public MessageService(MessageRepository messageRepository,
                          UserService userService,
                          RentalService rentalService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.rentalService = rentalService;
    }

    public Message sendMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public MessageResponseDTO processMessage(MessageDTO messageDTO, String userEmail) {
        Rental rental = rentalService.getRentalById(messageDTO.getRentalId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Message message = new Message();
        message.setRentalId(rental.getId());
        message.setMessage(messageDTO.getMessage());
        message.setUserId(Math.toIntExact(user.getId()));

        Message savedMessage = sendMessage(message);

        return new MessageResponseDTO(
                savedMessage.getId(),
                savedMessage.getRentalId(),
                savedMessage.getUserId(),
                "Message sent with success",
                savedMessage.getCreatedAt(),
                savedMessage.getUpdatedAt()
        );
    }
}
