package com.epam.goalTracker.dto;

import com.epam.goalTracker.entities.enums.Gender;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

/**
 * User DTO
 *
 * @author Fazliddin Makhsudov
 */
@Data
@ToString
public class UserDto {

    private long id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String encryptedPassword;
    private Date birthdate;
    private Gender gender;
    private Collection<RoleDto> roles;

}
