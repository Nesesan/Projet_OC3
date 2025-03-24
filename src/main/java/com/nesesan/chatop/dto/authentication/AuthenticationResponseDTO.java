package com.nesesan.chatop.dto.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDTO {
    private String token;

    public AuthenticationResponseDTO(String token) {
        this.token = token;
    }
}
