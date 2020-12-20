package com.epam.goalTracker.api;

import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.api.models.PersonalGoalRequestModel;
import com.epam.goalTracker.api.models.PersonalGoalResponseModel;
import com.epam.goalTracker.services.PersonalGoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity create(@RequestBody PersonalGoalRequestModel requestModel) {
        System.out.println("controller: " + requestModel);
        requestModel.setSeason(requestModel.getSeason().toUpperCase());
        PersonalGoalDomain personalGoalDomain = modelMapper.map(requestModel, PersonalGoalDomain.class);
        System.out.println("controller:2 " + requestModel);
        PersonalGoalDomain createdPersonalGoalDomain =
                personalGoalService.createPersonalGoal(requestModel.getUserid(), requestModel.getGlobalGoalid(), personalGoalDomain);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
