package com.epam.goalTracker.api;

import com.epam.goalTracker.services.domains.UserDomain;
import com.epam.goalTracker.api.models.UserRequestModel;
import com.epam.goalTracker.api.models.UserResponseModel;
import com.epam.goalTracker.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;

    }

    @GetMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable long id) {
        UserDomain user = userService.findUserById(id);
        UserResponseModel responseModel = modelMapper.map(user, UserResponseModel.class);
        return ResponseEntity.ok(responseModel);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateUser(@PathVariable long id,
                                     @RequestBody UserRequestModel userRequestModel) {
        //TODO check validation of userRequestModel fields
        UserDomain userDomain = modelMapper.map(userRequestModel, UserDomain.class);
        userService.updateUser(id, userDomain);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/location/{location}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable String location) {

        List<UserDomain> userDomainList = userService.findAllUsersByLocation(location);
        List<UserResponseModel> userResponseModels = userDomainList.stream()
                .map(userDto -> modelMapper.map(userDto, UserResponseModel.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponseModels);
    }
}
