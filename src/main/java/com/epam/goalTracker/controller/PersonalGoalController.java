package com.epam.goalTracker.controller;

import com.epam.goalTracker.dto.PersonalGoalDto;
import com.epam.goalTracker.dto.RoleDto;
import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.entities.PersonalGoalEntity;
import com.epam.goalTracker.entities.RoleEntity;
import com.epam.goalTracker.entities.UserEntity;
import com.epam.goalTracker.entities.enums.Role;
import com.epam.goalTracker.models.AuthRequestModel;
import com.epam.goalTracker.models.AuthResponseModel;
import com.epam.goalTracker.models.PersonalGoalRequestModel;
import com.epam.goalTracker.models.PersonalGoalResponseModel;
import com.epam.goalTracker.service.PersonalGoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 19:01
 */
@RestController
@CrossOrigin
@RequestMapping("/personal-goal")
public class PersonalGoalController {

    private ModelMapper modelMapper;
    private PersonalGoalService personalGoalService;

    @Autowired
    public PersonalGoalController(ModelMapper modelMapper, PersonalGoalService personalGoalService) {
        this.modelMapper = modelMapper;
        this.personalGoalService = personalGoalService;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PersonalGoalRequestModel requestDto) {

        PersonalGoalDto personalGoalDto = modelMapper.map(requestDto, PersonalGoalDto.class);
        PersonalGoalDto createdPersonalGoalDto = personalGoalService.createPersonalGoal(personalGoalDto);
        PersonalGoalEntity personalGoalEntity = modelMapper.map(createdPersonalGoalDto, PersonalGoalEntity.class);
        PersonalGoalResponseModel responseModel = new PersonalGoalResponseModel();
        responseModel.setId(personalGoalEntity.getId());
        responseModel.setName(personalGoalEntity.getName());
        responseModel.setClosed(personalGoalEntity.isClosed());
        responseModel.setStartDate(personalGoalEntity.getStartDate());
        responseModel.setEndDate(personalGoalEntity.getEndDate());
        responseModel.setPausedDate(personalGoalEntity.getPausedDate());
        responseModel.setGlobalGoalId(personalGoalEntity.getGlobalGoal().getId());
        responseModel.setUserId(personalGoalEntity.getUser().getId());
        return new ResponseEntity(responseModel, HttpStatus.CREATED);
    }
}
