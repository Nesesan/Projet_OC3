package com.nesesan.chatop.dto.authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthenticationRequestDTO is a data transfer object used to capture login
 * credentials from a client during the authentication process.
 * This DTO encapsulates the user's email and password, which are generally provided
 * as a part of the login request body, and are subsequently utilized for verifying
 * the user's identity.
 * Its properties are typically populated in the context of an API request
 * (e.g., via a REST controller) before being passed to the authentication service.
 */
@Getter
@Setter
public class AuthenticationRequestDTO {

    private String email;
    private String password;
}
