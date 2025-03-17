package com.nesesan.chatop.controller;

import com.nesesan.chatop.dto.message.MessageDTO;
import com.nesesan.chatop.dto.message.MessageResponseDTO;
import com.nesesan.chatop.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * This controller handles endpoints related to messages.
 * It facilitates sending messages linked to specific rentals by users.
 * Features:
 * - Accepts message data in the form of MessageDTO and processes it to link
 *   to a rental and user.
 * - Returns the result of the message operation as a MessageResponseDTO.
 * - Uses the authenticated user's information, provided via Principal,
 *   for associating the message with the user.
 * Endpoints:
 * - POST /api/messages: Allows users to submit a message.
 *   Requires a MessageDTO payload and user authentication.
 * Dependencies:
 * - MessageService: Handles the message processing logic including
 *   associating the message with the appropriate rental and user.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {


    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> sendMessage(@RequestBody MessageDTO messageDTO, Principal principal) {
        MessageResponseDTO responseDTO = messageService.processMessage(messageDTO, principal.getName());
        return ResponseEntity.ok(responseDTO);
    }
}
