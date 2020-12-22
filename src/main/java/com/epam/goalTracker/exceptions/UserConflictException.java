package com.epam.goalTracker.exceptions;

/**
 * User conflict exception
 *
 * @author Fazliddin Makhsudov
 */
public class UserConflictException extends UserServiceException {

    public UserConflictException(String message) {
        super(message);
    }
}
