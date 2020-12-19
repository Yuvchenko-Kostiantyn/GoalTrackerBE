package com.epam.goalTracker.api.models;


import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * User request model
 *
 * @author Fazliddin Makhsudov
 */
@Getter
@Setter
public class UserRequestModel {
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String gender;
    private Date birthdate;
    private String location;
}
