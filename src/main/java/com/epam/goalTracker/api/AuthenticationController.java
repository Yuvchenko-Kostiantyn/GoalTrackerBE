package com.epam.goalTracker.api;


import com.epam.goalTracker.api.models.AuthRequestModel;
import com.epam.goalTracker.api.models.AuthResponseModel;
import com.epam.goalTracker.api.models.UserRequestModel;
import com.epam.goalTracker.repositories.entities.RoleEntity;
import com.epam.goalTracker.repositories.entities.UserEntity;
import com.epam.goalTracker.repositories.entities.enums.Role;
import com.epam.goalTracker.security.jwt.JwtTokenProvider;
import com.epam.goalTracker.services.UserService;
import com.epam.goalTracker.services.domains.JwtTokenBlackListDomain;
import com.epam.goalTracker.services.domains.RoleDomain;
import com.epam.goalTracker.services.domains.UserDomain;
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

    private final ModelMapper modelMapper;

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
    public ResponseEntity processToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        JwtTokenBlackListDomain jwtTokenBlackListDomain = userService.findToken(token);
        if (jwtTokenBlackListDomain != null) {
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
        UserDomain userDomain = modelMapper.map(userRequestModel, UserDomain.class);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(Role.USER.name());
        RoleDomain roleDomain = modelMapper.map(roleEntity, RoleDomain.class);
        userDomain.setRoles(Arrays.asList(roleDomain));
        UserDomain createdUser = userService.createUser(userDomain);
        UserEntity userEntity = modelMapper.map(createdUser, UserEntity.class);
        AuthResponseModel responseModel = new AuthResponseModel();
        responseModel.setEmail(userEntity.getEmail());
        responseModel.setId(userEntity.getId());
        String token = jwtTokenProvider.createToken(userEntity.getEmail(), (List<RoleEntity>) userEntity.getRoles());
        responseModel.setToken(token);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.AUTHORIZATION, token);

        return new ResponseEntity(responseModel, responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequestModel requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            UserDomain user = userService.findUserByEmail(email);
            UserEntity foundUser = modelMapper.map(user, UserEntity.class);

            AuthResponseModel responseModel = new AuthResponseModel();
            responseModel.setEmail(foundUser.getEmail());
            responseModel.setId(foundUser.getId());
            String token = jwtTokenProvider.createToken(email, (List<RoleEntity>) foundUser.getRoles());
            responseModel.setToken(token);
            for (RoleEntity roleEntity : foundUser.getRoles()) {
                if (roleEntity.getName().equals(Role.ADMIN.name())) {
                    responseModel.setAdmin(true);
                    break;
                }
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.AUTHORIZATION, token);
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(responseModel);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
