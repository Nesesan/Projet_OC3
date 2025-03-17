package com.nesesan.chatop.dto.authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * UserRegisterDTO is a data transfer object designed to encapsulate the necessary
 * information required for user registration actions.
 *
 * This class holds three properties: name, email, and password, which are typically
 * provided by the client during the registration process. These fields are used to
 * create and persist a new user in the system.
 *
 * The parameters of the constructor allow for the initialization of this DTO with
 * user-provided data, generally obtained from an API request body.
 *
 * This class is typically utilized in services or controllers that handle user
 * registration functionality.
 */
@Getter
@Setter
public class UserRegisterDTO {

    private String name;
    private String email;
    private String password;

    public UserRegisterDTO (String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
