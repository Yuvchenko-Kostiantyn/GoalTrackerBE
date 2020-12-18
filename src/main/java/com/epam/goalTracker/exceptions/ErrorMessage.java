package com.epam.goalTracker.exceptions;

import lombok.Data;

import java.util.Date;

/**
 * Error message
 *
 * @author Fazliddin Makhsudov
 */
@Data
public class ErrorMessage {

    private final Date timestamp;
    private final String message;

    @Override
    public String toString() {
        return "{\n" +
                "\"timestamp\":\"" + timestamp + "\",\n " +
                "\"message\":\"" + message + "\"\n" +
                "}";
    }
}
