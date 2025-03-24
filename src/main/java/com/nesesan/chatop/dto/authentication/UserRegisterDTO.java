package com.nesesan.chatop.dto.authentication;

import lombok.Getter;
import lombok.Setter;

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
