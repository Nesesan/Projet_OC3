package com.nesesan.chatop.Exceptions;

/**
 * The ResourceNotFoundException is a custom exception that indicates
 * a specific resource could not be found.
 *
 * This exception extends RuntimeException and is typically used in scenarios
 * where a requested resource (e.g., an entity from a database) is not available
 * or cannot be located. It is unchecked and can be thrown during runtime without
 * the need for explicit handling.
 *
 * Example cases for usage could include situations where an entity lookup by ID
 * or email address fails, and proper response logic for missing resources is required.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String userNotFound) {
    }
}
