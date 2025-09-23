package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dto.GenreDTO;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.model.Genre;
import com.example.librarymanagementsystem.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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