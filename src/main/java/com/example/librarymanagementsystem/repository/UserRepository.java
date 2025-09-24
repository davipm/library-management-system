package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing User entities in the library management system.
 * It extends JpaRepository to provide CRUD operations and additional query methods for the User entity.
 *
 * Methods:
 * - findByUsername(String username): Retrieves a User entity based on its username.
 * - findByEmail(String email): Retrieves a User entity based on its email.
 * - existsByUsername(String username): Checks if a User entity exists with the given username.
 * - existsByEmail(String email): Checks if a User entity exists with the given email.
 *
 * This interface is used to interact with the database for operations related to the User entity,
 * such as fetching, validating, and verifying user credentials or information.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
}