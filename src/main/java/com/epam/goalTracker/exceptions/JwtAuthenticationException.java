package com.epam.goalTracker.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * JWT authentification exception
 *
 * @author Fazliddin Makhsudov
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
