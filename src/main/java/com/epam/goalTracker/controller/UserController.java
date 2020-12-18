package com.epam.goalTracker.controller;

import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.models.UserRequestModel;
import com.epam.goalTracker.models.UserResponseModel;
import com.epam.goalTracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @CrossOrigin
    @GetMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable long id) {

        UserDto user = userService.findById(id);
        UserResponseModel responseModel = modelMapper.map(user, UserResponseModel.class);
        return ResponseEntity.ok(responseModel);
    }

    @CrossOrigin
    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateUser(@PathVariable long id,
                                     @RequestBody UserRequestModel userRequestModel) {
        //TODO check validation of userRequestModel fields
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        userService.updateUser(id, userDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
