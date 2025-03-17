package com.nesesan.chatop.service;

import com.nesesan.chatop.model.Rental;
import com.nesesan.chatop.model.User;
import com.nesesan.chatop.repository.RentalRepository;
import com.nesesan.chatop.request.RentalRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public RentalService(RentalRepository rentalRepository, UserService userService, CloudinaryService cloudinaryService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    public Map<String, List<Rental>> getAllRentals(){
        List<Rental> rentals = rentalRepository.findAll().stream() .toList();

        Map<String, List<Rental>> response = new HashMap<>();
        response.put("rentals", rentals);

        return response;
    }
    public Optional<Rental> getRentalById(Integer id){
        return rentalRepository.findById(id);
    }

    public Rental createRental(Rental rental, String userEmail) throws Exception {
        if (userEmail == null || userEmail.isEmpty()) {
            throw new Exception("User not found");
        }
       // User user = userService.findByEmail(userEmail);

//        Rental newRental = new Rental();
//        newRental.setName(rental.getName());
//        newRental.setSurface(rental.getSurface());
//        newRental.setPrice(rental.getPrice());
//        newRental.setDescription(rental.getDescription());
//        newRental.setOwnerId(user.getId());
//        newRental.setCreatedAt(newRental.getCreatedAt());
//        newRental.setUpdatedAt(newRental.getUpdatedAt());
//
////        if (newRental.getPicture() != null && !newRental.getPicture().isEmpty()) {
//            String pictureUrl = cloudinaryService.uploadFile(newRental.getPicture());
//            newRental.setPicture(pictureUrl);
//        }

        return rentalRepository.save(rental);
    };



    public Rental updateRental(Integer id, RentalRequest rentalRequest) throws Exception{
       Rental rental = getRentalById(id)
               .orElseThrow(()-> new IllegalArgumentException("Rental not found"));

       rental.setName(rentalRequest.getName());
       rental.setSurface(rentalRequest.getSurface());
       rental.setPrice(rentalRequest.getPrice());
       rental.setDescription(rentalRequest.getDescription());
       rental.setUpdatedAt(rental.getUpdatedAt());

        return rentalRepository.save(rental);
    }
}
