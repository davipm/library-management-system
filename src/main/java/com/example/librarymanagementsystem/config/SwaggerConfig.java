package com.example.librarymanagementsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger and OpenAPI documentation.
 * This class customizes the OpenAPI documentation for the Library Management System API,
 * providing details such as title, version, description, contact information, and licensing,
 * as well as configuring security schemes for JWT-based authentication.
 *
 * Annotations:
 * - {@code @Configuration}: Indicates that this class is a configuration class used by Spring for application context setup.
 *
 * Beans:
 * - {@code customOpenAPI}: Configures an OpenAPI bean with customized API metadata and security settings.
 *
 * API Metadata Configuration:
 * - API Title: "Library Management System API".
 * - API Version: "1.0".
 * - Description: A RESTful API for managing genres, authors, and books in the library system.
 * - Contact Information: Includes contact name and email for API support.
 * - Licensing: Configured with "Apache 2.0" license details.
 *
 * Security Configuration:
 * - Adds support for JWT-based authentication using "bearerAuth" as the security scheme.
 * - Defines "bearerAuth" as an HTTP authentication scheme with JWT bearer tokens.
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management System API")
                        .version("1.0")
                        .description("RESTful API for managing genres, authors, and books")
                        .contact(new Contact()
                                .name("Library API Support")
                                .email("support@library.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}