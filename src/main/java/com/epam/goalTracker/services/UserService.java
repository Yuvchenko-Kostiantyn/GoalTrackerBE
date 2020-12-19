package com.epam.goalTracker.services;

import com.epam.goalTracker.services.domains.JwtTokenBlackListDomain;
import com.epam.goalTracker.services.domains.UserDomain;

import java.util.List;

/**
 * User service
 *
 * @author Yevhenii Kravtsov
 * @author Fazliddin Makhsudov
 */
public interface UserService {

    /**
     * 
     * @param userDomain user data
     * @return user
     */
    UserDomain createUser(UserDomain userDomain);

    UserDomain findUserById(long id);

    UserDomain findUserByEmail(String email);

    UserDomain updateUser(long id, UserDomain user);

    List<UserDomain> findAll();

    void deleteUserById(long id);

    void deleteUserByEmail(String email);

    JwtTokenBlackListDomain findToken(String token);

    void saveTokenToBlackList(String token);

    List<UserDomain> findAllUsersByLocation(String location); /// for cashing
}
