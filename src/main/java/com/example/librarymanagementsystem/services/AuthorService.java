package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dto.AuthorDTO;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing authors in the library management system.
 *
 * This class provides methods for performing CRUD operations on authors,
 * including creating, retrieving, updating, and deleting author entries.
 * It also includes utility methods for converting between {@code Author} entities
 * and {@code AuthorDTO} objects.
 *
 * Features:
 * - Retrieve all authors from the repository.
 * - Fetch a specific author by their ID.
 * - Create a new author entry, ensuring no duplicates by name.
 * - Update an existing author's details after validating the ID and name conflicts.
 * - Delete an author only if no associated books exist within the system.
 *
 * This class uses the {@code AuthorRepository} for database interactions and transactions.
 *
 * Exceptions:
 * - Throws {@code ResourceNotFoundException} when an author with the specified ID is not found.
 * - Throws {@code IllegalArgumentException} for invalid or duplicate data.
 * - Throws {@code IllegalStateException} when attempting to delete an author with associated books.
 *
 * Annotated with:
 * - {@code @Service} to denote it as a service layer component for dependency injection.
 * - {@code @Transactional} to ensure all operations are wrapped in a transaction context.
 */
@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return convertToDTO(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        if (authorRepository.existsByName(authorDTO.getName())) {
            throw new IllegalArgumentException("Author with name '" + authorDTO.getName() + "' already exists");
        }

        Author author = convertToEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return convertToDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        // Check if name is being changed and if the new name already exists
        if (!existingAuthor.getName().equals(authorDTO.getName()) &&
                authorRepository.existsByName(authorDTO.getName())) {
            throw new IllegalArgumentException("Author with name '" + authorDTO.getName() + "' already exists");
        }

        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setBiography(authorDTO.getBiography());
        existingAuthor.setBirthDate(authorDTO.getBirthDate());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return convertToDTO(updatedAuthor);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        // Check if author has associated books
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new IllegalStateException("Cannot delete author with associated books");
        }

        authorRepository.deleteById(id);
    }

    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBiography(author.getBiography());
        dto.setBirthDate(author.getBirthDate());
        return dto;
    }

    private Author convertToEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setBiography(dto.getBiography());
        author.setBirthDate(dto.getBirthDate());
        return author;
    }
}