package com.nesesan.chatop.controller;

import com.nesesan.chatop.dto.rental.RentalRequestDTO;
import com.nesesan.chatop.dto.rental.RentalResponseDTO;
import com.nesesan.chatop.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * RentalController is a REST controller that provides endpoints for managing rental properties.
 * It handles HTTP requests related to retrieving, creating, and updating rental records.
 */
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<RentalResponseDTO>>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRentalDTOById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<RentalResponseDTO> createRental(@ModelAttribute RentalRequestDTO rentalRequestDTO, Principal principal) throws IOException {
        RentalResponseDTO responseDTO = rentalService.createRental(rentalRequestDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<RentalResponseDTO> updateRental(@PathVariable Integer id,
                                                          @ModelAttribute RentalRequestDTO rentalRequestDTO,
                                                          Principal principal) {

        rentalService.verifyOwnership(id, principal.getName());
        RentalResponseDTO responseDTO = rentalService.updateRental(id, rentalRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}