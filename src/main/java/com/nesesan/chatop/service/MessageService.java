package com.nesesan.chatop.service;

import com.nesesan.chatop.model.Message;
import com.nesesan.chatop.model.Rental;
import com.nesesan.chatop.model.User;
import com.nesesan.chatop.repository.MessageRepository;
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

    public Message processMessage(Message message, String userEmail) {
        Rental rental =  rentalService.getRentalById(message.getRentalId())
                .orElseThrow(()-> new IllegalArgumentException("Rental not found"));

        User user = userService.findByEmail(userEmail);

        Message newMessage = new Message();
        message.setRentalId(rental.getId());
        message.setMessage(message.getMessage());
        message.setUserId(user.getId());

        Message savedMessage = sendMessage(newMessage);
        return newMessage;
    }
}
