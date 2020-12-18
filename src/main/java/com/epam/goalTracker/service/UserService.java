package com.epam.goalTracker.service;

import com.epam.goalTracker.dto.JwtTokenBlackListDto;
import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.entities.UserEntity;
import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto findById(long id);
    UserDto findByEmail(String email);
    UserDto updateUser(long id, UserDto user);
    List<UserDto> findAll();
    void delete(long id);
    void delete(String email);
    JwtTokenBlackListDto getToken(String token);
    void saveTokenToBlackList(String token);
}
