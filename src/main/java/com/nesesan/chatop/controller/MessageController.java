package com.nesesan.chatop.controller;

import com.nesesan.chatop.model.Message;
import com.nesesan.chatop.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/messages")
public class MessageController {


    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message, Principal principal) {
        Message messageResponse = messageService.processMessage(message,principal.getName());
        return ResponseEntity.ok(messageResponse);
    }
}
