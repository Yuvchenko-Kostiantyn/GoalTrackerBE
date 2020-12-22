package com.epam.goalTracker.exceptions;

/**
 * User service exception
 *
 * @author Fazliddin Makhsudov
 */
public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
    }
}
