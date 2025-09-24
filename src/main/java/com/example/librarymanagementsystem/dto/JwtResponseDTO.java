package com.example.librarymanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for JWT token response")
public class JwtResponseDTO {

    @Schema(description = "JWT authentication token")
    private String token;

    public JwtResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
