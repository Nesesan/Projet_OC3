package com.nesesan.chatop.controller;

import com.nesesan.chatop.dto.authentication.AuthenticationRequestDTO;
import com.nesesan.chatop.dto.authentication.AuthenticationResponseDTO;
import com.nesesan.chatop.dto.authentication.UserRegisterDTO;
import com.nesesan.chatop.dto.user.UserDTO;
import com.nesesan.chatop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody UserRegisterDTO registerDTO) {
        String token = userService.registerUser(registerDTO);
        return ResponseEntity.ok(new AuthenticationResponseDTO(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO authRequestDTO) {
        String token = userService.loginUser(authRequestDTO.getEmail(), authRequestDTO.getPassword());
        return ResponseEntity.ok(new AuthenticationResponseDTO(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        UserDTO userDTO = userService.getUserDTOByEmail(principal.getName());
        return ResponseEntity.ok(userDTO);
    }}