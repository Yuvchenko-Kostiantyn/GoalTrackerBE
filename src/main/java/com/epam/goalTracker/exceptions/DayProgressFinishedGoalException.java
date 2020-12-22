package com.epam.goalTracker.exceptions;

/**
 * DayProgressNotFoundException
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 12:15
 */
public class DayProgressFinishedGoalException extends DayProgressServiceException {
    public DayProgressFinishedGoalException(String message) {
        super(message);
    }
}
