package com.epam.goalTracker.exceptions;

/**
 * Personal goal not found exception
 *
 * @author Fazliddin Makhsudov
 */
public class PersonalGoalNotFoundException extends UserServiceException {

    public PersonalGoalNotFoundException(String message) {
        super(message);
    }
}
