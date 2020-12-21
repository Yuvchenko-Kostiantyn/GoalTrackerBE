package com.epam.goalTracker.exceptions;

/**
 * Personal badge not found exception
 *
 * @author Fazliddin Makhsudov
 */
public class PersonalBadgeNotFoundException extends UserServiceException {

    public PersonalBadgeNotFoundException(String message) {
        super(message);
    }
}
