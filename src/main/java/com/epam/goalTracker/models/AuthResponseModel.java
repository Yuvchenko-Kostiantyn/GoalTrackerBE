package com.epam.goalTracker.models;

import lombok.Data;
import lombok.ToString;

/**
 * Authentification request model
 *
 * @author Fazliddin Makhsudov
 */
@Data
@ToString
public class AuthResponseModel {

    private long id;
    private String email;
    private boolean isAdmin;
    private String token;
}
