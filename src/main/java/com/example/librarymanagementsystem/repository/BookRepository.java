package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Book entities in the library management system.
 * It extends JpaRepository to provide CRUD operations and additional query methods for the Book entity.
 *
 * Methods:
 * - findByIsbn(String isbn): Retrieves a Book entity based on its ISBN.
 * - existsByIsbn(String isbn): Checks the existence of a Book entity by ISBN.
 * - findByAuthorId(Long authorId): Retrieves a list of Book entities associated with a specific author's ID.
 * - findByGenreId(Long genreId): Retrieves a list of Book entities associated with a specific genre's ID.
 *
 * This interface is used to interact with the database for operations related to the Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Long authorId);

    @Query("SELECT b FROM Book b WHERE b.genre.id = :genreId")
    List<Book> findByGenreId(@Param("genreId") Long genreId);
}
