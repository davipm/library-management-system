package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dto.BookDTO;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Genre;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Book entities within the library management system.
 * Provides operations for CRUD operations related to books, including creating, reading,
 * updating, and deleting book records. This class ensures that business logic and validations are applied
 * before interacting with the underlying repositories.
 * <p>
 * Responsibilities:
 * - Fetching all books from the database.
 * - Retrieving a single book by its unique identifier (ID).
 * - Adding a new book to the database after validating its details.
 * - Updating an existing book's information, ensuring unique constraints such as ISBN are maintained.
 * - Deleting a book based on its unique identifier (ID).
 * <p>
 * Dependencies:
 * - BookRepository: To manage the data access layer for Book entities.
 * - AuthorRepository: To fetch and validate associated Author entities.
 * - GenreRepository: To fetch and validate associated Genre entities.
 * <p>
 * Methods:
 * - getAllBooks: Fetches all books from the database and converts them into DTOs.
 * - getBookById: Retrieves the details of a book by its ID, throwing an exception if not found.
 * - createBook: Creates a new book entity using the provided DTO and persists it in the database.
 * - updateBook: Updates the details of an existing book identified by its ID with the new data provided in the DTO.
 * - deleteBook: Deletes a book by its ID after verifying its existence.
 * <p>
 * Utility Methods:
 * - convertToDTO: Converts a Book entity into a Data Transfer Object (DTO) for response purposes.
 * - convertToEntity: Converts a Data Transfer Object (DTO) into a Book entity for persistence.
 * <p>
 * Exceptions:
 * - ResourceNotFoundException: Thrown when a book, author, or genre is not found in the database.
 * - IllegalArgumentException: Thrown when invalid data, such as non-unique ISBN, is provided.
 * <p>
 * Annotations:
 * - @Service: Marks the class as a Spring service component.
 * - @Transactional: Ensures that database operations are executed in a transactional context.
 */
@Service
@Transactional
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;

  public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
  }

  public List<BookDTO> getAllBooks() {
    return bookRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public BookDTO getBookById(Long id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    return convertToDTO(book);
  }

  public BookDTO createBook(BookDTO bookDTO) {
    if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
      throw new IllegalArgumentException("Book with ISBN '" + bookDTO.getIsbn() + "' already exists");
    }

    Author author = authorRepository.findById(bookDTO.getAuthorId())
        .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));

    Genre genre = genreRepository.findById(bookDTO.getGenreId())
        .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + bookDTO.getGenreId()));

    Book book = convertToEntity(bookDTO);
    book.setAuthor(author);
    book.setGenre(genre);

    Book savedBook = bookRepository.save(book);
    return convertToDTO(savedBook);
  }

  public BookDTO updateBook(Long id, BookDTO bookDTO) {
    Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

    // Check if ISBN is being changed and if the new ISBN already exists
    if (!existingBook.getIsbn().equals(bookDTO.getIsbn()) &&
        bookRepository.existsByIsbn(bookDTO.getIsbn())) {
      throw new IllegalArgumentException("Book with ISBN '" + bookDTO.getIsbn() + "' already exists");
    }

    Author author = authorRepository.findById(bookDTO.getAuthorId())
        .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));

    Genre genre = genreRepository.findById(bookDTO.getGenreId())
        .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + bookDTO.getGenreId()));

    existingBook.setTitle(bookDTO.getTitle());
    existingBook.setIsbn(bookDTO.getIsbn());
    existingBook.setPublicationDate(bookDTO.getPublicationDate());
    existingBook.setDescription(bookDTO.getDescription());
    existingBook.setAuthor(author);
    existingBook.setGenre(genre);

    Book updatedBook = bookRepository.save(existingBook);
    return convertToDTO(updatedBook);
  }

  public void deleteBook(Long id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

    bookRepository.deleteById(id);
  }

  private BookDTO convertToDTO(Book book) {
    BookDTO dto = new BookDTO();
    dto.setId(book.getId());
    dto.setTitle(book.getTitle());
    dto.setIsbn(book.getIsbn());
    dto.setPublicationDate(book.getPublicationDate());
    dto.setDescription(book.getDescription());
    dto.setAuthorId(book.getAuthor() != null ? book.getAuthor().getId() : null);
    dto.setGenreId(book.getGenre() != null ? book.getGenre().getId() : null);
    return dto;
  }

  private Book convertToEntity(BookDTO dto) {
    Book book = new Book();
    book.setTitle(dto.getTitle());
    book.setIsbn(dto.getIsbn());
    book.setPublicationDate(dto.getPublicationDate());
    book.setDescription(dto.getDescription());
    return book;
  }
}