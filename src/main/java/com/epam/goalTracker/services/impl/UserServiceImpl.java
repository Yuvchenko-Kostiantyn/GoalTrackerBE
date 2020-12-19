package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.services.domains.JwtTokenBlackListDomain;
import com.epam.goalTracker.services.domains.RoleDomain;
import com.epam.goalTracker.services.domains.UserDomain;
import com.epam.goalTracker.repositories.entities.JwtTokenBlackListEntity;
import com.epam.goalTracker.repositories.entities.RoleEntity;
import com.epam.goalTracker.repositories.entities.UserEntity;
import com.epam.goalTracker.repositories.entities.enums.Role;
import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.UserConflictException;
import com.epam.goalTracker.exceptions.UserNotFoundException;
import com.epam.goalTracker.repositories.JwtTokenBlackListRepository;
import com.epam.goalTracker.repositories.RoleRepository;
import com.epam.goalTracker.repositories.UserRepository;
import com.epam.goalTracker.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenBlackListRepository jwtTokenBlackListRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           JwtTokenBlackListRepository jwtTokenBlackListRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenBlackListRepository = jwtTokenBlackListRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDomain createUser(UserDomain userDomain) {
        log.info("Saving a new user " + userDomain);
        if (userRepository.findByEmail(userDomain.getEmail()) != null) {
            throw new UserConflictException("User already exists");
        }
        UserEntity userEntity = modelMapper.map(userDomain, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDomain.getPassword()));
        Collection<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity roleEntity = roleRepository.findByName(Role.USER.name());
        if (roleEntity != null) {
            roleEntities.add(roleEntity);
        }
        userEntity.setRoles(roleEntities);

        UserEntity storedUser = userRepository.save(userEntity);

        return modelMapper.map(storedUser, UserDomain.class);
    }

    @Override
    public UserDomain findUserById(long id) {
        UserEntity userEntity = getUserEntityById(id);
        return modelMapper.map(userEntity, UserDomain.class);
    }

    @Override
    public UserDomain findUserByEmail(String email) {
        log.info("Getting a person by email: " + email);
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(ErrorMessages.NO_USER_FOUND_BY_EMAIL + email);
        }
        return modelMapper.map(userEntity, UserDomain.class);
    }

    @Override
    public UserDomain updateUser(long id, UserDomain userDomain) {
        UserEntity userEntity = getUserEntityById(id);
        String ROLE_USER = "USER";
        String ROLE_ADMIN = "ADMIN";
        userEntity.setFirstName(userDomain.getFirstName());
        userEntity.setSecondName(userDomain.getSecondName());
        userEntity.setEmail(userDomain.getEmail());
        userEntity.setGender(userDomain.getGender());
        userEntity.setBirthdate(userDomain.getBirthdate());
        userEntity.setLocation(userDomain.getLocation());
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDomain.getPassword()));
        if (userDomain.getRoles() != null) {
            List<RoleEntity> roleList = new ArrayList<>();
            roleList.add(roleRepository.findByName(ROLE_USER));
            for (RoleDomain roleDomain : userDomain.getRoles()) {
                if (roleDomain.getName().equals(ROLE_ADMIN)) {
                    roleList.add(roleRepository.findByName(ROLE_ADMIN));
                }
            }
            userEntity.setRoles(roleList);
        }
        UserEntity storedUser = userRepository.save(userEntity);
        return modelMapper.map(storedUser, UserDomain.class);
    }

    @Override
    public void deleteUserById(long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity != null) {
            log.info("Deleting user with id: " + userEntity.getId());
            userRepository.delete(userEntity);
        } else {
            throw new RuntimeException("No such user");
        }
    }

    @Override
    public void deleteUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            log.info("Deleting user with email: " + userEntity.getEmail());
            userRepository.delete(userEntity);
        } else {
            throw new RuntimeException("No such user");
        }
    }

    @Override
    public List<UserDomain> findAll() {
        log.info("Getting a list of all users");
        return userRepository.findAll().stream().map(userEntity -> modelMapper.map(userEntity, UserDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public JwtTokenBlackListDomain findToken(String token) {
        JwtTokenBlackListEntity jwtTokenBlackListEntity = jwtTokenBlackListRepository.findByToken(token);
        if (jwtTokenBlackListEntity == null) {
            return null;
        }
        return modelMapper.map(jwtTokenBlackListEntity, JwtTokenBlackListDomain.class);
    }

    @Override
    public void saveTokenToBlackList(String token) {
        JwtTokenBlackListEntity jwtTokenBlackListEntity = new JwtTokenBlackListEntity();
        jwtTokenBlackListEntity.setToken(token);
        jwtTokenBlackListEntity.setDateOfAdding(LocalDateTime.now());
        jwtTokenBlackListRepository.save(jwtTokenBlackListEntity);
    }

    @Override
    public List<UserDomain> findAllUsersByLocation(String location) {
        return userRepository.findByLocation(location).stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDomain.class))
                .collect(Collectors.toList());
    }

    private UserEntity getUserEntityById(long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException(ErrorMessages.NO_USER_FOUND);
        }
        return userEntity;
    }
}
