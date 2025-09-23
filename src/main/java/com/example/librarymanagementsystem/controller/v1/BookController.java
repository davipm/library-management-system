package com.example.librarymanagementsystem.controller.v1;

import com.example.librarymanagementsystem.dto.BookDTO;
import com.example.librarymanagementsystem.services.BookService;
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

/**
 * Controller class for managing books in the library system. Provides endpoints
 * for retrieving, creating, updating, and deleting books.
 *
 * Annotations:
 * - @RestController: Marks this class as a REST controller.
 * - @RequestMapping("/api/v1/books"): Base path for all book-related endpoints.
 * - @Tag(name = "Books", description = "Book management API"): OpenAPI tag for API documentation.
 *
 * Security:
 * All endpoints are secured and require specific roles (`USER` or `ADMIN`) for access.
 */
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Book management API")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)))
    })

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID of the book to retrieve") @PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Create a new book", description = "Create a new book with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Author or genre not found"),
            @ApiResponse(responseCode = "409", description = "Book with this ISBN already exists")
    })

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDTO> createBook(
            @Parameter(description = "Book details") @Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing book", description = "Update an existing book with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Book, author, or genre not found"),
            @ApiResponse(responseCode = "409", description = "Book with this ISBN already exists")
    })

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDTO> updateBook(
            @Parameter(description = "ID of the book to update") @PathVariable Long id,
            @Parameter(description = "Updated book details") @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @Operation(summary = "Delete a book", description = "Delete a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to delete") @PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}