package com.nesesan.chatop.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


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
