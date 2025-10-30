package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Genre;
import com.example.librarymanagementsystem.model.User;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.GenreRepository;
import com.example.librarymanagementsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;
  private final PasswordEncoder passwordEncoder;

  public DataLoader(UserRepository userRepository, BookRepository bookRepository,
                    AuthorRepository authorRepository, GenreRepository genreRepository,
                    PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    // Create users
    if (!userRepository.existsByUsername("user")) {
      User user = new User();
      user.setUsername("user");
      user.setEmail("user@example.com");
      user.setPassword(passwordEncoder.encode("password"));
      user.setRole("ROLE_USER");
      userRepository.save(user);
    }

    if (!userRepository.existsByUsername("admin")) {
      User admin = new User();
      admin.setUsername("admin");
      admin.setEmail("admin@example.com");
      admin.setPassword(passwordEncoder.encode("admin"));
      admin.setRole("ROLE_ADMIN");
      userRepository.save(admin);
    }

    // Create genres
    if (!genreRepository.existsByName("Fiction")) {
      Genre fiction = new Genre();
      fiction.setName("Fiction");
      genreRepository.save(fiction);
    }

    if (!genreRepository.existsByName("Non-Fiction")) {
      Genre nonfiction = new Genre();
      nonfiction.setName("Non-Fiction");
      genreRepository.save(nonfiction);
    }

    // Create authors
    if (!authorRepository.existsByName("Author One")) {
      Author author1 = new Author();
      author1.setName("Author One");
      authorRepository.save(author1);
    }

    if (!authorRepository.existsByName("Author Two")) {
      Author author2 = new Author();
      author2.setName("Author Two");
      authorRepository.save(author2);
    }

    // Create books
    if (!bookRepository.existsByTitle("Book One")) {
      Book book1 = new Book();
      book1.setTitle("Book One");
      book1.setAuthor(authorRepository.findByName("Author One").get());
      book1.setGenre(genreRepository.findByName("Fiction").get());
      bookRepository.save(book1);
    }

    if (!bookRepository.existsByTitle("Book Two")) {
      Book book2 = new Book();
      book2.setTitle("Book Two");
      book2.setAuthor(authorRepository.findByName("Author Two").get());
      book2.setGenre(genreRepository.findByName("Non-Fiction").get());
      bookRepository.save(book2);
    }
  }
}
