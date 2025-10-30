package com.example.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) class representing a book.
 * This class is used to transfer data related to books between different layers
 * of the application, such as between controllers, services, or repositories.
 * <p>
 * The class includes attributes like the book's ID, title, ISBN, publication date,
 * description, author ID, and genre ID. It is designed to ensure data validity
 * through the use of validation annotations.
 * <p>
 * Use this class to model book-related data and perform necessary operations
 * with information related to books within the application.
 */
@Setter
@Getter
public class BookDTO {
  // Getters and Setters
  private Long id;

  @NotBlank(message = "Book title is required")
  @Size(max = 200, message = "Book title must be less than 200 characters")
  private String title;

  @Size(max = 13, message = "ISBN must be less than 13 characters")
  private String isbn;

  private LocalDate publicationDate;

  @Size(max = 1000, message = "Description must be less than 1000 characters")
  private String description;

  @NotNull(message = "Author ID is required")
  private Long authorId;

  @NotNull(message = "Genre ID is required")
  private Long genreId;

  // Constructors
  public BookDTO() {
  }
}
