package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Genre entities in the library management system.
 * It extends JpaRepository to provide CRUD operations and additional query methods for the Genre entity.
 * <p>
 * Methods:
 * - {@code findByName(String name)}: Retrieves a Genre entity based on its name.
 * - {@code existsByName(String name)}: Checks the existence of a Genre entity by its name.
 * <p>
 * This interface is used to interact with the database for operations related to the Genre entity.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
  Optional<Genre> findByName(String name);

  boolean existsByName(String name);
}
