package com.epam.goalTracker.exceptions;

/**
 * User not found exception
 *
 * @author Fazliddin Makhsudov
 */
public class GlobalBadgeNotFoundException extends UserServiceException {

    public GlobalBadgeNotFoundException(String message) {
        super(message);
    }
}
