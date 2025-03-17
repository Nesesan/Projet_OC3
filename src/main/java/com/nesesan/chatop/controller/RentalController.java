package com.nesesan.chatop.controller;

import com.nesesan.chatop.model.Rental;
import com.nesesan.chatop.model.User;
import com.nesesan.chatop.request.RentalRequest;
import com.nesesan.chatop.service.CloudinaryService;
import com.nesesan.chatop.service.RentalService;
import com.nesesan.chatop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private UserService userService;
    private RentalService rentalService;
    private CloudinaryService cloudinaryService;

    public RentalController(UserService userService, RentalService rentalService, CloudinaryService cloudinary) {
        this.userService = userService;
        this.rentalService = rentalService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public ResponseEntity <Map<String, List<Rental>>>getAllRentals(){
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Rental>>getRentalById(@PathVariable Integer id){
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }
    @PostMapping(consumes = {"multipart/form-data"})
   public ResponseEntity<Rental> createReantal(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture,
            Principal principal) throws Exception {
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);

        User user = userService.findByEmail(principal.getName());
        rental.setOwnerId(user.getId());

        if (picture != null && !picture.isEmpty()) {
            String pictureUrl = cloudinaryService.uploadFile(String.valueOf((picture)));
            rental.setPicture(pictureUrl);
        }

        Rental newRental = rentalService.createRental(rental, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(newRental);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/data-form")
    public ResponseEntity<Rental> updateRental(@PathVariable Integer id,
                                                                          @ModelAttribute RentalRequest rentalRequest,
                                                                          Principal principal) throws Exception {
        Rental rental = rentalService.updateRental(id, rentalRequest);
        return ResponseEntity.ok(rental);
    }
}
