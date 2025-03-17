package com.nesesan.chatop.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private String created_at;
    private String updated_at;

    public UserResponse(Integer id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = createdAt.format(formatter);
        this.updated_at = updatedAt.format(formatter);
    }


    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
}

