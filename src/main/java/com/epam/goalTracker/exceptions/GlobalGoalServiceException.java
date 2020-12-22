package com.epam.goalTracker.exceptions;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 12:15
 */
public class GlobalGoalServiceException extends RuntimeException {
    public GlobalGoalServiceException(String message) {
        super(message);
    }
}
