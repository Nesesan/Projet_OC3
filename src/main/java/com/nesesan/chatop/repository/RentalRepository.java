package com.nesesan.chatop.repository;

import com.nesesan.chatop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Rental} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for data access.
 * It is annotated with {@code @Repository}, indicating that it is a Spring
 * component responsible for data persistence and retrieval.
 */
@Repository
public interface RentalRepository extends JpaRepository  <Rental, Integer>{
}
