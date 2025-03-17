package com.nesesan.chatop.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Data Transfer Object (DTO) for representing a User.
 *
 * This class is designed to encapsulate user data for API communication
 * or business layer processes. It includes basic user information such
 * as ID, name, email, and timestamps for tracking creation and updates.
 *
 * The timestamps are serialized and deserialized in "yyyy/MM/dd" format
 * for consistent date handling in external systems or APIs.
 *
 * This class can be instantiated using either the primary constructor
 * with LocalDateTime fields or an auxiliary constructor that accepts
 * java.util.Date objects and converts them to LocalDateTime.
 */
@Data
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime updatedAt;

    public UserDTO(Integer id, String name, String email, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.updatedAt = updatedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}

