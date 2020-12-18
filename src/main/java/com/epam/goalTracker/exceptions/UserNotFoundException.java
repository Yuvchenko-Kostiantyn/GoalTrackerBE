package com.epam.goalTracker.exceptions;

/**
 * User not found exception
 *
 * @author Fazliddin Makhsudov
 */
public class UserNotFoundException extends UserServiceException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
