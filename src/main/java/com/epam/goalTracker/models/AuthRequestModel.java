package com.epam.goalTracker.models;

import lombok.Data;

/**
 * Authentification request model
 *
 * @author Fazliddin Makhsudov
 */
@Data
public class AuthRequestModel {
    private String email;
    private String password;
}

