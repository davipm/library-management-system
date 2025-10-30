package com.example.librarymanagementsystem.security;

import com.example.librarymanagementsystem.model.User;
import com.example.librarymanagementsystem.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Service implementation of the {@link UserDetailsService} interface for loading user-specific data.
 * This class is used in the context of Spring Security to retrieve user details from the database
 * and convert them into a UserDetails object for authentication purposes.
 * <p>
 * The service relies on the {@link UserRepository} to fetch user data by username from the underlying data source.
 * <p>
 * Responsibilities:
 * - Retrieve a user entity by the provided username.
 * - Throw a {@link UsernameNotFoundException} if the user does not exist.
 * - Map the user's roles to security-granted authorities to be used by Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameOrEmail(username, username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        mapRolesToAuthorities(Collections.singleton(user.getRole()))
    );
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles) {
    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}