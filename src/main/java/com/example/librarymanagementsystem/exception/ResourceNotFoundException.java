package com.example.librarymanagementsystem.exception;

/**
 * The ResourceNotFoundException is a custom exception that is thrown when
 * a requested resource cannot be found.
 *
 * This exception extends RuntimeException and is intended to be used in service
 * or repository layers where a specific resource, such as a book, author, or genre,
 * does not exist or cannot be retrieved by its identifier.
 *
 * When this exception is thrown, it is typically handled by a central exception
 * handler to provide an appropriate HTTP response with a 404 Not Found status
 * and a meaningful error message.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}