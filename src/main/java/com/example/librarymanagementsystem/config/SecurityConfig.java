package com.example.librarymanagementsystem.config;

import com.example.librarymanagementsystem.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up application security.
 * This class configures Spring Security for handling authentication, authorization,
 * and other security aspects of the application, including JWT-based authentication.
 *
 * Annotations:
 * - {@code @Configuration}: Indicates that this class is a configuration class.
 * - {@code @EnableWebSecurity}: Enables Spring Security for the application.
 * - {@code @EnableMethodSecurity}: Enables method-level security with pre/post authorization.
 *
 * Beans:
 * - {@code PasswordEncoder}: Provides a BCrypt-based password encoder.
 * - {@code AuthenticationManager}: Provides the authentication manager required for authentication processes.
 * - {@code SecurityFilterChain}: Configures the security filter chain for handling HTTP requests.
 *
 * Security Configuration:
 * - Disables CSRF protection.
 * - Configures stateless session management to work with JWTs.
 * - Defines public, ROLE_USER, and ROLE_ADMIN access controls for specific API endpoints.
 * - Adds a filter to handle JWT token authentication for incoming requests.
 *
 * Dependency:
 * - {@code JwtTokenProvider}: A component used for managing JWT tokens, including creating, parsing, and validating tokens.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/v1/genres/**", "/api/v1/authors/**", "/api/v1/books/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenProvider, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}