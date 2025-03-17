package com.nesesan.chatop.dto.authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthenticationResponseDTO is a data transfer object used to encapsulate the
 * response details during the authentication process.
 * This DTO contains a single property, a token, which represents an authentication
 * or session token provided to the client after successful authentication.
 * Its primary usage is within authentication endpoints, where it is returned in
 * the response body to indicate a successful login or registration along with the
 * generated token.
 */
@Getter
@Setter
public class AuthenticationResponseDTO {
    private String token;

    public AuthenticationResponseDTO(String token) {
        this.token = token;
    }
}
