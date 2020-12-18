package com.epam.goalTracker.models;

import com.epam.goalTracker.dto.RoleDto;
import lombok.Data;

import java.util.Collection;

/**
 * Rest user model
 *
 * @author Fazliddin Makhsudov
 */
@Data
public class RestUserModel {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    Collection<RoleDto> roles;
}
