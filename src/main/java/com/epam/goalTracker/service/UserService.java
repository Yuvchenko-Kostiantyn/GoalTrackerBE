package com.epam.goalTracker.service;

import com.epam.goalTracker.dto.JwtTokenBlackListDto;
import com.epam.goalTracker.dto.PersonalGoalDto;
import com.epam.goalTracker.dto.UserDto;

import java.util.List;

/**
 * User service
 *
 * @author Yevhenii Kravtsov
 * @author Fazliddin Makhsudov
 */
public interface UserService {

    /**
     * Creates user dto
     * 
     * @param userDto user data
     * @return user dto
     */
    UserDto createUser(UserDto userDto);

    UserDto findUserById(long id);

    UserDto findUserByEmail(String email);

    UserDto updateUser(long id, UserDto user);

    List<UserDto> findAll();

    void deleteUserById(long id);

    void deleteUserByEmail(String email);

    JwtTokenBlackListDto findToken(String token);

    void saveTokenToBlackList(String token);

    List<UserDto> findAllUsersByLocation(String location); /// for cashing
}
