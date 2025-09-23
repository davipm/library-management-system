package com.example.librarymanagementsystem.exception;

/**
 * The UnauthorizedException is a custom runtime exception that is thrown
 * when an unauthorized access attempt is detected.
 *
 * Typically used in cases where the client attempts to access a resource
 * or perform an action that requires authentication or proper authorization.
 *
 * When this exception is thrown, it is handled by a centralized exception
 * handler, which responds with a 401 Unauthorized HTTP status code
 * and an appropriate error message.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}