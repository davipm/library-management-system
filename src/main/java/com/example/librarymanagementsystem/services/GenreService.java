package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dto.GenreDTO;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.model.Genre;
import com.example.librarymanagementsystem.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing genres in the library management system.
 *
 * This class provides methods for creating, retrieving, updating, and deleting genres.
 * It acts as the intermediary layer between the controller and the repository,
 * implementing the business logic for genre-related operations.
 *
 * Key Methods:
 * - getAllGenres: Retrieves a list of all genres in the system.
 * - getGenreById: Retrieves a specific genre based on its ID.
 * - createGenre: Adds a new genre, with validation to prevent duplicates.
 * - updateGenre: Updates an existing genre, with validation on name uniqueness.
 * - deleteGenre: Deletes a genre, ensuring it has no associated entities like books.
 *
 * Utility Methods:
 * - convertToDTO: Transforms a Genre entity into its corresponding DTO.
 * - convertToEntity: Converts a GenreDTO into a Genre entity.
 *
 * This service ensures data integrity and performs necessary validations
 * such as checking for duplicate names and preventing deletion of genres
 * with associated books.
 *
 * Annotations:
 * - {@code @Service}: Marks this class as a Spring service component.
 * - {@code @Transactional}: Provides transactional boundaries around methods
 *   to ensure consistent database operations.
 */
@Service
@Transactional
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GenreDTO getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));
        return convertToDTO(genre);
    }

    public GenreDTO createGenre(GenreDTO genreDTO) {
        if (genreRepository.existsByName(genreDTO.getName())) {
            throw new IllegalArgumentException("Genre with name '" + genreDTO.getName() + "' already exists");
        }

        Genre genre = convertToEntity(genreDTO);
        Genre savedGenre = genreRepository.save(genre);
        return convertToDTO(savedGenre);
    }

    public GenreDTO updateGenre(Long id, GenreDTO genreDTO) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));

        // Check if name is being changed and if the new name already exists
        if (!existingGenre.getName().equals(genreDTO.getName()) &&
                genreRepository.existsByName(genreDTO.getName())) {
            throw new IllegalArgumentException("Genre with name '" + genreDTO.getName() + "' already exists");
        }

        existingGenre.setName(genreDTO.getName());
        existingGenre.setDescription(genreDTO.getDescription());

        Genre updatedGenre = genreRepository.save(existingGenre);
        return convertToDTO(updatedGenre);
    }

    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));

        // Check if genre has associated books
        if (genre.getBooks() != null && !genre.getBooks().isEmpty()) {
            throw new IllegalStateException("Cannot delete genre with associated books");
        }

        genreRepository.deleteById(id);
    }

    private GenreDTO convertToDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());
        return dto;
    }

    private Genre convertToEntity(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());
        return genre;
    }
}