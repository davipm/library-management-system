package com.example.librarymanagementsystem.controller.v1;

import com.example.librarymanagementsystem.dto.GenreDTO;

import com.example.librarymanagementsystem.services.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@Tag(name = "Genres", description = "Genre management API")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Operation(summary = "Get all genres", description = "Retrieve a list of all genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of genres",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDTO.class)))
    })

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @Operation(summary = "Get genre by ID", description = "Retrieve a specific genre by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved genre",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDTO.class))),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GenreDTO> getGenreById(
            @Parameter(description = "ID of the genre to retrieve") @PathVariable Long id) {
        GenreDTO genre = genreService.getGenreById(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @Operation(summary = "Create a new genre", description = "Create a new genre with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Genre with this name already exists")
    })

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenreDTO> createGenre(
            @Parameter(description = "Genre details") @Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO createdGenre = genreService.createGenre(genreDTO);
        return new ResponseEntity<>(createdGenre, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing genre", description = "Update an existing genre with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenreDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "409", description = "Genre with this name already exists")
    })

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenreDTO> updateGenre(
            @Parameter(description = "ID of the genre to update") @PathVariable Long id,
            @Parameter(description = "Updated genre details") @Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO updatedGenre = genreService.updateGenre(id, genreDTO);
        return new ResponseEntity<>(updatedGenre, HttpStatus.OK);
    }

    @Operation(summary = "Delete a genre", description = "Delete a genre by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "409", description = "Cannot delete genre with associated books")
    })

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGenre(
            @Parameter(description = "ID of the genre to delete") @PathVariable Long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}