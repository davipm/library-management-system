package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an author entity in the library management system.
 * The Author class provides details about an author including their name, biography, birth date,
 * and a list of books authored by them.
 *
 * This entity is mapped to the "authors" table in the database and contains the following fields:
 * - id: A unique identifier for the author.
 * - name: The name of the author, which is required and must be less than 100 characters.
 * - biography: A brief biography of the author, optional, but must be less than 500 characters.
 * - birthDate: The birth date of the author, optional.
 * - books: A collection of books written by the author, represented as a one-to-many relationship.
 *
 * This class is used within the library management system to maintain information about
 * authors and their related books. It supports basic CRUD operations for managing author data.
 */
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author name is required")
    @Size(max = 100, message = "Author name must be less than 100 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Biography must be less than 500 characters")
    private String biography;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    public Author() {}

    public Author(String name, String biography, LocalDate birthDate) {
        this.name = name;
        this.biography = biography;
        this.birthDate = birthDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
