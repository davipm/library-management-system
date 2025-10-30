package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Represents a user entity in the system.
 * The User class contains properties related to a user's identity, credentials, and roles in the system.
 * <p>
 * This entity is mapped to the "users" table in the database and includes the following fields:
 * - id: A unique identifier for the user, generated automatically.
 * - username: The username of the user, required and must be unique, with a maximum length of 50 characters.
 * - email: The email address of the user, required, unique, valid, and with a maximum length of 100 characters.
 * - password: The password of the user, required, with a length between 6 and 100 characters.
 * - roles: A collection of roles assigned to the user, stored in a separate table ("user_roles").
 * <p>
 * This class is used to manage user-related data such as authentication, authorization, and profile management.
 * It supports basic operations such as reading, creation, updating, and managing user roles.
 */
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
  // Getters and Setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Username is required")
  @Size(max = 50, message = "Username must be less than 50 characters")
  @Column(unique = true, nullable = false)
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email must be less than 100 characters")
  @Column(unique = true, nullable = false)
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
  @Column(nullable = false)
  private String password;

  @Column(name = "role")
  private String role;

  // Constructors
  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
