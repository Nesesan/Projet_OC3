package com.nesesan.chatop.repository;

import com.nesesan.chatop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for data access.
 * It is annotated with {@code @Repository} to indicate
 * that it is a Spring Data repository.
 */
@Repository
public interface UserRepository extends JpaRepository <User, Integer>{
    Optional<User> findByEmail(String email);
}
