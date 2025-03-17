package com.nesesan.chatop.dto.rental;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing the response for a rental entity.
 *
 * This class provides relevant information about a rental property
 * including its metadata, owner information, and timestamps. It is used
 * as a response object in API endpoints to encapsulate rental property
 * details.
 *
 * Key features:
 * - Encapsulates essential details such as rental ID, name, surface area,
 *   price, description, and an optional picture URL.
 * - Includes information about the owner of the rental, identified via
 *   an owner ID.
 * - Provides time-based metadata including creation and last update
 *   timestamps, formatted using ISO 8601 standard.
 * - Contains an optional message field, which can be used for additional
 *   context or status information.
 */
@Getter
@Setter
public class RentalResponseDTO {
    private Integer id;
    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;
    private  String message;

    @JsonProperty("owner_id")
    private Integer ownerId;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public RentalResponseDTO(Integer id, String name, Double surface, Double price, String picture, String description,
                             Integer ownerId, LocalDateTime createdAt, LocalDateTime updatedAt, String message) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }
}
