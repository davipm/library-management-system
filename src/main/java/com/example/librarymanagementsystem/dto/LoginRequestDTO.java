package com.example.librarymanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data Transfer Object for user login")
public class LoginRequestDTO {

    @Schema(description = "Username of the user", example = "john.doe", required = true)
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "Password of the user", example = "password123", required = true)
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
