package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.AuthorDTO;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British author", LocalDate.of(1903, 6, 25));
        author.setId(1L);

        authorDTO = new AuthorDTO("George Orwell", "British author", LocalDate.of(1903, 6, 25));
        authorDTO.setId(1L);
    }

    @Test
    void getAllAuthors() {
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDTO> result = authorService.getAllAuthors();

        assertEquals(1, result.size());
        assertEquals("George Orwell", result.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO result = authorService.getAuthorById(1L);

        assertEquals("George Orwell", result.getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void getAuthorByIdNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));
    }

    @Test
    void createAuthor() {
        when(authorRepository.existsByName("George Orwell")).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO result = authorService.createAuthor(authorDTO);

        assertEquals("George Orwell", result.getName());
        verify(authorRepository, times(1)).existsByName("George Orwell");
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void createAuthorAlreadyExists() {
        when(authorRepository.existsByName("George Orwell")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> authorService.createAuthor(authorDTO));
    }

    @Test
    void updateAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.existsByName("Eric Arthur Blair")).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO updatedDTO = new AuthorDTO("Eric Arthur Blair", "British author", LocalDate.of(1903, 6, 25));
        AuthorDTO result = authorService.updateAuthor(1L, updatedDTO);

        assertEquals("Eric Arthur Blair", result.getName());
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).existsByName("Eric Arthur Blair");
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void deleteAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(1L));
    }
}