package com.epam.goalTracker.models;

import lombok.Data;

import java.util.Date;

/**
 * Update user model
 *
 * @author Fazliddin Makhsudov
 */
@Data
public class UserResponseModel {
    private String firstName;
    private String secondName;
    private String email;
    private String gender;
    private Date birthdate;
}
