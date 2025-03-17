package com.nesesan.chatop.Exceptions;

/**
 * The UnauthorizedException is a custom exception that signifies a lack of
 * authorization for performing a specific action or accessing a resource.
 *
 * This exception extends RuntimeException and is typically used in scenarios
 * where an operation is attempted by a user or system without the required
 * permissions or credentials. It is unchecked and can be thrown during runtime
 * without the need for explicit handling.
 *
 * Common use cases include:
 * - Access control checks for restricted operations or resources.
 * - Validation of user roles and permissions during a request.
 * - Scenarios where authentication is successful but authorization fails.
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
