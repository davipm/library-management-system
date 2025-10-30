package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a genre entity in the library management system.
 * The Genre class is used to categorize books based on their genre type, such as Fiction, Non-fiction,
 * Science Fiction, Mystery, and more.
 * <p>
 * This entity is mapped to the "genres" table in the database and includes the following fields:
 * - id: A unique identifier for the genre, generated automatically.
 * - name: The name of the genre, which is required and must be unique. It is limited to 100 characters.
 * - description: An optional description for the genre, with a maximum length of 500 characters.
 * - books: A list of books associated with this genre, represented as a one-to-many relationship.
 * <p>
 * This class serves as a core entity within the library management system to manage the organization
 * and categorization of books.
 */
@Setter
@Getter
@Entity
@Table(name = "genres")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Genre name is required")
  @Size(max = 100, message = "Genre name must be less than 100 characters")
  @Column(unique = true, nullable = false)
  private String name;

  @Size(max = 500, message = "Description must be less than 500 characters")
  private String description;

  @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Book> books;

  public Genre() {
  }

  public Genre(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
