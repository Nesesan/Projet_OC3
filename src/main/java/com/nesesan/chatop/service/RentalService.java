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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing rental operations.
 *
 * This class provides methods to perform operations such as retrieving rentals,
 * creating new rentals, updating existing rentals, fetching detailed rental information,
 * and verifying ownership.
 *
 * It interacts with the RentalRepository for database operations, UserService for user-related
 * operations, and CloudinaryService for handling cloud storage functionalities.
 */
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
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        Map<String, List<RentalResponseDTO>> response = new HashMap<>();
        response.put("rentals", rentals); // Encapsuler dans la clé "rentals"
        return response;
    }

    private RentalResponseDTO convertToDTO(Rental rental) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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
                "Rental updated successfully");
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

        return new RentalResponseDTO(
                createdRental.getId(),
                createdRental.getName(),
                createdRental.getSurface(),
                createdRental.getPrice(),
                createdRental.getPicture(),
                createdRental.getDescription(),
                createdRental.getOwnerId(),
                createdRental.getCreatedAt(),
                createdRental.getUpdatedAt(),
                "Rental created!"
        );

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

        return new RentalResponseDTO(
                updatedRental.getId(),
                updatedRental.getName(),
                updatedRental.getSurface(),
                updatedRental.getPrice(),
                updatedRental.getPicture(),
                updatedRental.getDescription(),
                updatedRental.getOwnerId(),
                updatedRental.getCreatedAt(),
                updatedRental.getUpdatedAt(),
                "Rental updated!"
        );
    }

    public RentalResponseDTO getRentalDTOById(Integer id) {
        Rental rental = getRentalById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        return convertToDTO(rental);
    }

    public void verifyOwnership(Integer rentalId, String email) {
        Rental rental = getRentalById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!(rental.getOwnerId() == user.getId().intValue())) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce rental");
        }
    }
}