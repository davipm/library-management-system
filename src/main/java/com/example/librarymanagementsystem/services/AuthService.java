package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dto.UserDTO;
import com.example.librarymanagementsystem.model.User;
import com.example.librarymanagementsystem.repository.UserRepository;
import com.example.librarymanagementsystem.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for handling authentication and user registration.
 *
 * This class provides functionalities to authenticate users and register new users.
 * It integrates with the Spring Security framework for authentication processes
 * and utilizes JWT for token-based authentication.
 *
 * Responsibilities:
 * - Authenticate users by validating their credentials and generating a JWT.
 * - Register new users with proper validation for unique usernames and emails.
 * - Convert User entities to UserDTO for external use.
 *
 * Dependencies:
 * - AuthenticationManager: Used for authenticating the user credentials.
 * - UserRepository: Provides access to user-related database operations.
 * - PasswordEncoder: Encodes user passwords before persisting to storage.
 * - JwtTokenProvider: Manages the generation of JWT tokens and associated operations.
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Map<String, String> authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String jwt = jwtTokenProvider.generateToken(user.getUsername(), user.getRole());

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("type", "Bearer");
        return response;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getRole() == null) {
            user.setRole("ROLE_USER");
        } else {
            user.setRole(userDTO.getRole());
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}