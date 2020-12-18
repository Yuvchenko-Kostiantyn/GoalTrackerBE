package com.cargohub.services.impl;

import com.cargohub.dto.BillingDetailsDto;
import com.cargohub.dto.JwtTokenBlackListDto;
import com.cargohub.dto.RoleDto;
import com.cargohub.dto.UserDto;
import com.cargohub.entities.BillingDetailsEntity;
import com.cargohub.entities.JwtTokenBlackListEntity;
import com.cargohub.entities.RoleEntity;
import com.cargohub.entities.UserEntity;
import com.cargohub.entities.extra.Roles;
import com.cargohub.exceptions.ErrorMessages;
import com.cargohub.exceptions.UserConflictException;
import com.cargohub.exceptions.UserNotFoundException;
import com.cargohub.repositories.JwtTokenBlackListRepository;
import com.cargohub.repositories.RoleRepository;
import com.cargohub.repositories.UserRepository;
import com.cargohub.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
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
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new UserConflictException("User already exists");
        }

        for (int i = 0; i < userDto.getBillingDetails().size(); i++) {
            BillingDetailsDto billingDetails = userDto.getBillingDetails().get(i);
            billingDetails.setUserDetails(userDto);
            userDto.getBillingDetails().set(i, billingDetails);
        }

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Collection<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity roleEntity = roleRepository.findByName(Roles.ROLE_USER.name());
        if (roleEntity != null) {
            roleEntities.add(roleEntity);
        }
        userEntity.setRoles(roleEntities);

        UserEntity storedUser = userRepository.save(userEntity);

        return modelMapper.map(storedUser, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(ErrorMessages.NO_USER_FOUND_BY_EMAIL + email);
        }

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserById(long id) {
        UserEntity userEntity = getUserEntityById(id);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(long id, UserDto user) {
        UserEntity userEntity = getUserEntityById(id);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        if (user.getRoles() != null) {
            List<RoleEntity> roleList = new ArrayList<>();
            roleList.add(roleRepository.findByName("ROLE_USER"));
            for (RoleDto roleDto : user.getRoles()) {
                if (roleDto.getName().equals("ROLE_ADMIN"))
                    roleList.add(roleRepository.findByName("ROLE_ADMIN"));
            }
            userEntity.setRoles(roleList);
        }
        UserEntity storedUser = userRepository.save(userEntity);

        return modelMapper.map(storedUser, UserDto.class);
    }

    @Override
    public boolean updateUserPassword(long id, String password) {
        UserEntity userEntity = getUserEntityById(id);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(password));
        UserEntity savedUser = userRepository.save(userEntity);
        return (savedUser != null);
    }

    @Override
    public boolean addUsersBillingDetailsCard(long id, BillingDetailsDto billingDetailsDto) {
        UserEntity userEntity = getUserEntityById(id);
        BillingDetailsEntity billingDetailsEntity = modelMapper.map(billingDetailsDto, BillingDetailsEntity.class);
        userEntity.getBillingDetails().add(billingDetailsEntity);
        billingDetailsEntity.setUserDetails(userEntity);
        UserEntity savedUSer = userRepository.save(userEntity);
        return (savedUSer.getBillingDetails().contains(billingDetailsEntity));
    }

    @Override
    public boolean removeUsersBillingDetailsCard(long id, long cardId) {
        UserEntity userEntity = getUserEntityById(id);

        for (BillingDetailsEntity b : userEntity.getBillingDetails()) {
            if (b.getId() == cardId) {
                userEntity.getBillingDetails().remove(b);
                userRepository.save(userEntity);
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteUser(long id) {
        UserEntity userEntity = getUserEntityById(id);

        userRepository.delete(userEntity);
    }

    @Override
    public Page<UserEntity> getUsers(int page, int limit) {

        if (page > 0) {
            page -= 1;
        }

        Pageable pageableRequest = PageRequest.of(page, limit);

        return userRepository.findAll(pageableRequest);
    }

    @Override
    public JwtTokenBlackListDto getToken(String token) {
        JwtTokenBlackListEntity jwtTokenBlackListEntity = jwtTokenBlackListRepository.findByToken(token);
        if(jwtTokenBlackListEntity == null){
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


    private UserEntity getUserEntityById(long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException(ErrorMessages.NO_USER_FOUND);
        }
        return userEntity;
    }
}
