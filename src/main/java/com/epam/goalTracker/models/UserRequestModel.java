package com.epam.goalTracker.models;


import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * Registration model
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
}
