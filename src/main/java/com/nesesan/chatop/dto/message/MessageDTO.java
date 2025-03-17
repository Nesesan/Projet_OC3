package com.nesesan.chatop.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for passing message details between different layers of the application.
 *
 * This class encapsulates the message content and the associated rental ID.
 * It is primarily used to transfer data when processing and sending messages in the application.
 *
 * Fields:
 * - `message`: The content of the message.
 * - `rentalId`: The identifier of the rental associated with the message.
 *
 * Annotations:
 * - `@Getter`, `@Setter`: Lombok annotations to generate getter and setter methods for the fields.
 * - `@JsonProperty("rental_id")`: Jackson annotation to map the `rentalId` field to JSON property `rental_id`.
 *
 * Constructor:
 * - Constructs a new `MessageDTO` object with the provided `message` and `rentalId`.
 */
@Getter
@Setter
public class MessageDTO {
    private String message;
    @JsonProperty("rental_id")
    private int rentalId;

    public MessageDTO(String message, int rentalId) {
        this.message = message;
        this.rentalId = rentalId;
    }

}
