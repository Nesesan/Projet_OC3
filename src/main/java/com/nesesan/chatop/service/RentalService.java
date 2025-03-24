package com.nesesan.chatop.service;

import com.nesesan.chatop.Exceptions.ResourceNotFoundException;
import com.nesesan.chatop.Exceptions.UnauthorizedException;
import com.nesesan.chatop.dto.rental.RentalRequestDTO;
import com.nesesan.chatop.dto.rental.RentalResponseDTO;
import com.nesesan.chatop.model.Rental;
import com.nesesan.chatop.model.User;
import com.nesesan.chatop.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public RentalService(RentalRepository rentalRepository,
                         UserService userService,
                         CloudinaryService cloudinaryService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    public Map<String, List<RentalResponseDTO>> getAllRentals() {
        List<RentalResponseDTO> rentals = rentalRepository.findAll().stream()
                .map(rental -> convertToDTO(rental, "Rental updated successfully"))
                .toList();

        return Collections.singletonMap("rentals", rentals);
    }


    private RentalResponseDTO convertToDTO(Rental rental, String message) {
        return new RentalResponseDTO(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwnerId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt(),
                message
        );
    }

    public Optional<Rental> getRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    public RentalResponseDTO createRental(RentalRequestDTO rentalRequestDTO, String userEmail) throws IOException {
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Rental rental = new Rental();
        rental.setName(rentalRequestDTO.getName());
        rental.setSurface(rentalRequestDTO.getSurface());
        rental.setPrice(rentalRequestDTO.getPrice());
        rental.setDescription(rentalRequestDTO.getDescription());
        rental.setOwnerId(Math.toIntExact(user.getId()));

        if (rentalRequestDTO.getPicture() != null && !rentalRequestDTO.getPicture().isEmpty()) {
            String pictureUrl = cloudinaryService.uploadFile(rentalRequestDTO.getPicture());
            rental.setPicture(pictureUrl);
        }

        Rental createdRental = rentalRepository.save(rental);
        return convertToDTO(createdRental, "Rental created!");
    }

    @Transactional
    public RentalResponseDTO updateRental(Integer id, RentalRequestDTO rentalRequestDTO) {
        Rental rental = getRentalById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        rental.setName(rentalRequestDTO.getName());
        rental.setSurface(rentalRequestDTO.getSurface());
        rental.setPrice(rentalRequestDTO.getPrice());
        rental.setDescription(rentalRequestDTO.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());

        Rental updatedRental = rentalRepository.save(rental);
        return convertToDTO(updatedRental, "Rental updated!");
    }

    public RentalResponseDTO getRentalDTOById(Integer id) {
        Rental rental = getRentalById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
        return convertToDTO(rental, "Rental retrieved successfully");
    }

    public void verifyOwnership(Integer rentalId, String email) {
        Rental rental = getRentalById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!Objects.equals(rental.getOwnerId(), Math.toIntExact(user.getId()))) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce rental");
        }
    }
}
