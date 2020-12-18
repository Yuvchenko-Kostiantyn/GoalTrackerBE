package com.epam.goalTracker.service.impl;

import com.epam.goalTracker.dto.JwtTokenBlackListDto;
import com.epam.goalTracker.dto.RoleDto;
import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.entities.JwtTokenBlackListEntity;
import com.epam.goalTracker.entities.RoleEntity;
import com.epam.goalTracker.entities.UserEntity;
import com.epam.goalTracker.entities.enums.Role;
import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.UserConflictException;
import com.epam.goalTracker.exceptions.UserNotFoundException;
import com.epam.goalTracker.repository.JwtTokenBlackListRepository;
import com.epam.goalTracker.repository.RoleRepository;
import com.epam.goalTracker.repository.UserRepository;
import com.epam.goalTracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
    public UserDto createUser(UserDto userDto) {
        log.info("Saving a new user " + userDto);
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new UserConflictException("User already exists");
        }
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        Collection<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity roleEntity = roleRepository.findByName(Role.USER.name());
        if (roleEntity != null) {
            roleEntities.add(roleEntity);
        }
        userEntity.setRoles(roleEntities);

        UserEntity storedUser = userRepository.save(userEntity);

        return modelMapper.map(storedUser, UserDto.class);
    }

    @Override
    public UserDto findUserById(long id) {
        UserEntity userEntity = getUserEntityById(id);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        log.info("Getting a person by email: " + email);
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(ErrorMessages.NO_USER_FOUND_BY_EMAIL + email);
        }
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        UserEntity userEntity = getUserEntityById(id);
        String ROLE_USER = "USER";
        String ROLE_ADMIN = "ADMIN";
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setSecondName(userDto.getSecondName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setGender(userDto.getGender());
        userEntity.setBirthdate(userDto.getBirthdate());
        userEntity.setLocation(userDto.getLocation());
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        if (userDto.getRoles() != null) {
            List<RoleEntity> roleList = new ArrayList<>();
            roleList.add(roleRepository.findByName(ROLE_USER));
            for (RoleDto roleDto : userDto.getRoles()) {
                if (roleDto.getName().equals(ROLE_ADMIN)) {
                    roleList.add(roleRepository.findByName(ROLE_ADMIN));
                }
            }
            userEntity.setRoles(roleList);
        }
        UserEntity storedUser = userRepository.save(userEntity);
        return modelMapper.map(storedUser, UserDto.class);
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
    public List<UserDto> findAll() {
        log.info("Getting a list of all users");
        return userRepository.findAll().stream().map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public JwtTokenBlackListDto findToken(String token) {
        JwtTokenBlackListEntity jwtTokenBlackListEntity = jwtTokenBlackListRepository.findByToken(token);
        if (jwtTokenBlackListEntity == null) {
            return null;
        }
        return modelMapper.map(jwtTokenBlackListEntity, JwtTokenBlackListDto.class);
    }

    @Override
    public void saveTokenToBlackList(String token) {
        JwtTokenBlackListEntity jwtTokenBlackListEntity = new JwtTokenBlackListEntity();
        jwtTokenBlackListEntity.setToken(token);
        jwtTokenBlackListEntity.setDateOfAdding(LocalDateTime.now());
        jwtTokenBlackListRepository.save(jwtTokenBlackListEntity);
    }

    @Override
    public List<UserDto> findAllUsersByLocation(String location) {
        return userRepository.findByLocation(location).stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
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
