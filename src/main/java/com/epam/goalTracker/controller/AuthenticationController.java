package com.epam.goalTracker.controller;


import com.epam.goalTracker.dto.JwtTokenBlackListDto;
import com.epam.goalTracker.dto.RoleDto;
import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.entities.RoleEntity;
import com.epam.goalTracker.entities.UserEntity;
import com.epam.goalTracker.entities.enums.Role;
import com.epam.goalTracker.models.AuthRequestModel;
import com.epam.goalTracker.models.AuthResponseModel;
import com.epam.goalTracker.models.UserRequestModel;
import com.epam.goalTracker.security.jwt.JwtTokenProvider;
import com.epam.goalTracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Authentification rest controller
 *
 * @author Fazliddin Makhsudov
 */
@CrossOrigin
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private ModelMapper modelMapper;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider, UserService userService,
                                    ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/signout")
    public ResponseEntity processToken (@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        JwtTokenBlackListDto jwtTokenBlackListDto = userService.findToken(token);
        if (jwtTokenBlackListDto != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userService.saveTokenToBlackList(token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/gettoken")
    public ResponseEntity logOut(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header",
                "Value-ResponseEntityBuilderWithHttpHeaders");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Response with header using ResponseEntity");
    }

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody UserRequestModel userRequestModel) {
        //TODO check validation of userRequestModel fields
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(Role.USER.name());
        RoleDto roleDto = modelMapper.map(roleEntity, RoleDto.class);
        userDto.setRoles(Arrays.asList(roleDto));
        UserDto createdUser = userService.createUser(userDto);
        UserEntity userEntity = modelMapper.map(createdUser, UserEntity.class);
        AuthResponseModel responseModel = new AuthResponseModel();
        responseModel.setEmail(userEntity.getEmail());
        responseModel.setId(userEntity.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.AUTHORIZATION,
                jwtTokenProvider.createToken(userEntity.getEmail(), (List<RoleEntity>) userEntity.getRoles()));
        return new ResponseEntity(responseModel, responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequestModel requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            UserDto user = userService.findUserByEmail(email);
            UserEntity foundUser = modelMapper.map(user, UserEntity.class);

            AuthResponseModel responseModel = new AuthResponseModel();
            responseModel.setEmail(foundUser.getEmail());
            responseModel.setId(foundUser.getId());
            String token = jwtTokenProvider.createToken(email, (List<RoleEntity>) foundUser.getRoles());
            for (RoleEntity roleEntity : foundUser.getRoles()) {
                if (roleEntity.getName().equals(Role.ADMIN.name())) {
                    responseModel.setAdmin(true);
                    break;
                }
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.AUTHORIZATION,
                    jwtTokenProvider.createToken(foundUser.getEmail(), (List<RoleEntity>) foundUser.getRoles()));
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(responseModel);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
