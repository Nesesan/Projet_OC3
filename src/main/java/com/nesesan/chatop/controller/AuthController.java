package com.nesesan.chatop.controller;

import com.nesesan.chatop.model.User;
import com.nesesan.chatop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String response = userService.registerUser(user);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("login");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me (String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
}
