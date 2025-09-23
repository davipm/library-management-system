package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.GenreDTO;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.model.Genre;
import com.example.librarymanagementsystem.repository.GenreRepository;
import com.example.librarymanagementsystem.services.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    private Genre genre;
    private GenreDTO genreDTO;

    @BeforeEach
    void setUp() {
        genre = new Genre("Fiction", "Fictional books");
        genre.setId(1L);

        genreDTO = new GenreDTO("Fiction", "Fictional books");
        genreDTO.setId(1L);
    }

    @Test
    void getAllGenres() {
        List<Genre> genres = Arrays.asList(genre);
        when(genreRepository.findAll()).thenReturn(genres);

        List<GenreDTO> result = genreService.getAllGenres();

        assertEquals(1, result.size());
        assertEquals("Fiction", result.get(0).getName());
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void getGenreById() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        GenreDTO result = genreService.getGenreById(1L);

        assertEquals("Fiction", result.getName());
        verify(genreRepository, times(1)).findById(1L);
    }

    @Test
    void getGenreByIdNotFound() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> genreService.getGenreById(1L));
    }

    @Test
    void createGenre() {
        when(genreRepository.existsByName("Fiction")).thenReturn(false);
        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        GenreDTO result = genreService.createGenre(genreDTO);

        assertEquals("Fiction", result.getName());
        verify(genreRepository, times(1)).existsByName("Fiction");
        verify(genreRepository, times(1)).save(any(Genre.class));
    }

    @Test
    void createGenreAlreadyExists() {
        when(genreRepository.existsByName("Fiction")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> genreService.createGenre(genreDTO));
    }

    @Test
    void updateGenre() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreRepository.existsByName("Science Fiction")).thenReturn(false);
        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        GenreDTO updatedDTO = new GenreDTO("Science Fiction", "Sci-fi books");
        GenreDTO result = genreService.updateGenre(1L, updatedDTO);

        assertEquals("Science Fiction", result.getName());
        verify(genreRepository, times(1)).findById(1L);
        verify(genreRepository, times(1)).existsByName("Science Fiction");
        verify(genreRepository, times(1)).save(any(Genre.class));
    }

    @Test
    void deleteGenre() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        genreService.deleteGenre(1L);

        verify(genreRepository, times(1)).findById(1L);
        verify(genreRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteGenreNotFound() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> genreService.deleteGenre(1L));
    }
}