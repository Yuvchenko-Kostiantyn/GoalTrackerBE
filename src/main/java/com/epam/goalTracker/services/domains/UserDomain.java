package com.epam.goalTracker.services.domains;

import com.epam.goalTracker.repositories.entities.enums.Gender;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;

/**
 * User DTO
 *
 * @author Fazliddin Makhsudov
 */
@Data
@ToString
public class UserDomain {

    private long id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String encryptedPassword;
    private Date birthdate;
    private Gender gender;
    private String location;
    private long scores;
    private Collection<RoleDomain> roles;
    private Collection<PersonalGoalDomain> personalGoalDomains;

}
