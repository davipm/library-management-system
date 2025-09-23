package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.services.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.librarymanagementsystem.controller.v1.GenreController;
import com.example.librarymanagementsystem.dto.GenreDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    void getAllGenres() throws Exception {
        GenreDTO genre1 = new GenreDTO("Fiction", "Fictional books");
        GenreDTO genre2 = new GenreDTO("Non-Fiction", "Non-fictional books");
        List<GenreDTO> genres = Arrays.asList(genre1, genre2);

        when(genreService.getAllGenres()).thenReturn(genres);

        mockMvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Fiction"))
                .andExpect(jsonPath("$[1].name").value("Non-Fiction"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createGenre() throws Exception {
        GenreDTO genreDTO = new GenreDTO("Science Fiction", "Sci-fi books");
        GenreDTO savedGenreDTO = new GenreDTO("Science Fiction", "Sci-fi books");
        savedGenreDTO.setId(1L);

        when(genreService.createGenre(any(GenreDTO.class))).thenReturn(savedGenreDTO);

        mockMvc.perform(post("/api/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Science Fiction"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createGenreInvalidData() throws Exception {
        GenreDTO genreDTO = new GenreDTO("", "Sci-fi books"); // Empty name

        mockMvc.perform(post("/api/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isBadRequest());
    }
}