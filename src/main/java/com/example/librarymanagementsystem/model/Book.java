package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents a book entity in the library management system.
 * The Book class contains properties related to a book, such as its title, ISBN, publication date,
 * a brief description, and its relationships to the author and genre.
 * <p>
 * This entity is mapped to the "books" table in the database and includes the following fields:
 * - id: A unique identifier for the book, generated automatically.
 * - title: The title of the book, which is required and must not exceed 200 characters.
 * - isbn: The International Standard Book Number (ISBN), optional but must be unique and less than 13 characters.
 * - publicationDate: The publication date of the book, optional.
 * - description: A brief description of the book, optional but must be less than 1000 characters.
 * - author: The author associated with the book, represented as a required many-to-one relationship.
 * - genre: The genre associated with the book, represented as a required many-to-one relationship.
 * <p>
 * This class serves as a core entity within the library management system to manage book-related data.
 * It supports reading, creation, updating, and management of book records in relation to other entities such as authors and genres.
 */
@Setter
@Getter
@Entity
@Table(name = "books")
public class Book {
  // Getters and Setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Book title is required")
  @Size(max = 200, message = "Book title must be less than 200 characters")
  @Column(nullable = false)
  private String title;

  @Size(max = 13, message = "ISBN must be less than 13 characters")
  @Column(unique = true)
  private String isbn;

  @Column(name = "publication_date")
  private LocalDate publicationDate;

  @Size(max = 1000, message = "Description must be less than 1000 characters")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  @NotNull(message = "Author is required")
  private Author author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id", nullable = false)
  @NotNull(message = "Genre is required")
  private Genre genre;

  public Book() {
  }
}
