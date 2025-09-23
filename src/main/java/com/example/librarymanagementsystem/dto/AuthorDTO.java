package com.example.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) class representing an author.
 * This class is used to transfer data related to authors between different layers
 * of the application, such as between the controller and service layers.
 *
 * The class contains attributes such as the author's ID, name, biography, and birth date.
 * It also provides validation annotations to ensure the correctness of data.
 *
 * Use this class to model author-related data in the application.
 */
public class AuthorDTO {
    private Long id;

    @NotBlank(message = "Author name is required")
    @Size(max = 100, message = "Author name must be less than 100 characters")
    private String name;

    @Size(max = 500, message = "Biography must be less than 500 characters")
    private String biography;

    private LocalDate birthDate;

    // Constructors
    public AuthorDTO() {}

    public AuthorDTO(String name, String biography, LocalDate birthDate) {
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
}
