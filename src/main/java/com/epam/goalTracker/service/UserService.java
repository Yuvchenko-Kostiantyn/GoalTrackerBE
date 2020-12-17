package com.epam.goalTracker.service;

import com.epam.goalTracker.entities.User;
import java.util.List;

public interface UserService {

    User create(User user);

    User findById(long id);
    User findByEmail(String email);
    User updateUser(User request);
    List<User> findAll();
    void delete(long id);
    void delete(String email);

}
