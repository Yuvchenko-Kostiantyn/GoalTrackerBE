package com.epam.goalTracker.service.impl;

import com.epam.goalTracker.entities.User;
import com.epam.goalTracker.repository.UserRepository;
import com.epam.goalTracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        log.info("Saving a new Person " + user.getFirstName() + " " + user.getSecondName());
        return userRepository.save(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        log.info("Getting a person by email: " + email);
        return userRepository.findByEmail(email);
    }


    @Override
    public User updateUser(User request) {
        User user = userRepository.findById(request.getId()).orElse(null);
        if (user != null) {
            log.info("Updating user with id: " + user.getId());
            user.setFirstName(request.getFirstName());
            user.setSecondName(request.getSecondName());
            user.setGender(request.getGender());
            user.setBirthdate(request.getBirthdate());
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            log.info("Deleting user with id: " + user.getId());
            userRepository.delete(user);
        } else {
            throw new RuntimeException("No such user");
        }
    }

    @Override
    public void delete(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            log.info("Deleting user with email: " + user.getEmail());
            userRepository.delete(user);
        } else {
            throw new RuntimeException("No such user");
        }
    }

    @Override
    public List<User> findAll() {
        log.info("Getting a list of all users");
        return userRepository.findAll();
    }
}
