package com.example.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a genre.
 * This class is used to transfer data related to genres between different layers
 * of the application, such as controllers, services, or repositories.
 * <p>
 * The class provides attributes like the genre's ID, name, and description.
 * Validation annotations are used to ensure that the data meets specific
 * constraints, such as requiring the name to be not blank and within a certain
 * character limit.
 * <p>
 * Use this class to model genre-related data and perform necessary operations
 * with information regarding genres in the application.
 */
public class GenreDTO {
  private long id;

  @Setter
  @Getter
  @NotBlank(message = "Genre name is required")
  @Size(max = 100, message = "Genre name must be less than 100 characters")
  private String name;

  @Setter
  @Getter
  @Size(max = 500, message = "Description must be less than 500 characters")
  private String description;

  // Constructors
  public GenreDTO() {
  }

  public GenreDTO(String name, String description) {
    this.name = name;
    this.description = description;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
