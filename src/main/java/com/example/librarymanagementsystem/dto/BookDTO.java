package com.example.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class BookDTO {
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
    public BookDTO() {}

    public BookDTO(String title, String isbn, LocalDate publicationDate, String description, Long authorId, Long genreId) {
        this.title = title;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.description = description;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
