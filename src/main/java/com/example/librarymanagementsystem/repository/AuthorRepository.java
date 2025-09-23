package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Author entities in the library management system.
 * This interface provides methods for querying and interacting with the underlying
 * "authors" database table.
 *
 * Extends {@link JpaRepository}, which provides basic CRUD operations as well as pagination and sorting capabilities.
 *
 * Methods:
 * - {@code findByName}: Retrieves an Author by their name.
 * - {@code existsByName}: Checks if an Author exists based on their name.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
    boolean existsByName(String name);
}
