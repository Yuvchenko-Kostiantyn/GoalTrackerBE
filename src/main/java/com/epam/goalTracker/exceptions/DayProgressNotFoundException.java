package com.epam.goalTracker.exceptions;

/**
 * DayProgressNotFoundException
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 12:15
 */
public class DayProgressNotFoundException extends DayProgressServiceException {
    public DayProgressNotFoundException(String message) {
        super(message);
    }
}
