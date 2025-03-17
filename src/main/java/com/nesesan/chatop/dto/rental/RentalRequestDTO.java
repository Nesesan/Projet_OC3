package com.nesesan.chatop.dto.rental;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object representing a rental request.
 * This class is used to capture the necessary details for creating or updating a rental entity.
 */
@Getter
@Setter
public class RentalRequestDTO {
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;

    public RentalRequestDTO(String name,
                            Double surface,
                            Double price,
                            MultipartFile picture,
                            String description) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
    }
}
