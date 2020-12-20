package com.epam.goalTracker.exceptions;

/**
 * DayProgressServiceException
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 12:15
 */
public class DayProgressServiceException extends RuntimeException{
    public DayProgressServiceException(String message) {
        super(message);
    }
}
