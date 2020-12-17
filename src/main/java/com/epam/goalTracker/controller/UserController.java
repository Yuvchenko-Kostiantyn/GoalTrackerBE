package com.epam.goalTracker.controller;
import ch.qos.logback.core.CoreConstants;
import com.epam.goalTracker.dto.request.UserRequest;
import com.epam.goalTracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
   // private final UserMapper userMapper;

    public UserController(UserService userService) {
        this.userService = userService;
       // this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest requestDto) {
        System.out.println("requestDto "+ requestDto);
        return ResponseEntity.ok(requestDto);
    }
}
