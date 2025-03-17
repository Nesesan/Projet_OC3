package com.nesesan.chatop.dto.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a Data Transfer Object (DTO) for providing the response
 * of message-related operations. This class is used to return detailed
 * information about a message, including its associated identifiers and
 * timestamps.
 *
 * The main purpose of this class is to encapsulate the data that is returned
 * to the client after processing a message.
 *
 * Fields include:
 * - `id`: Unique identifier for the message.
 * - `rentalId`: Identifier for the rental associated with the message.
 * - `userId`: Identifier of the user who created the message.
 * - `message`: Content of the message.
 * - `createdAt`: Timestamp indicating when the message was created.
 * - `updatedAt`: Timestamp indicating when the message was last updated.
 *
 * Annotations include:
 * - `@Getter` and `@Setter`: Lombok annotations for generating the getter and setter methods.
 * - `@JsonFormat`: Specifies the JSON formatting for date/time fields (`createdAt` and `updatedAt`).
 *
 * Constructor:
 * - Creates a new instance of `MessageResponseDTO` with all fields initialized.
 *   This constructor is intended to initialize all relevant properties when
 *   constructing the object for response purposes.
 */
@Getter
@Setter
public class MessageResponseDTO {
    private int id;
    private int rentalId;
    private int userId;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime updatedAt;

    public MessageResponseDTO(Integer id,
                              Integer rentalId,
                              Integer userId,
                              String message,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
