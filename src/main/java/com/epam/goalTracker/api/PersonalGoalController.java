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

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PersonalGoalRequestModel requestDto) {

        System.out.println("1 " + requestDto);
        PersonalGoalDomain personalGoalDomain = modelMapper.map(requestDto, PersonalGoalDomain.class);
        System.out.println("2 " + personalGoalDomain);
        PersonalGoalDomain createdPersonalGoalDomain =
                personalGoalService.createPersonalGoal(requestDto.getUserId(), personalGoalDomain);
        System.out.println("3  " + createdPersonalGoalDomain);



        PersonalGoalEntity personalGoalEntity = modelMapper.map(createdPersonalGoalDomain, PersonalGoalEntity.class);

        PersonalGoalResponseModel responseModel = new PersonalGoalResponseModel();
        responseModel.setId(personalGoalEntity.getId());
        responseModel.setName(personalGoalEntity.getName());

        responseModel.setStartDate(personalGoalEntity.getStartDate());
        responseModel.setEndDate(personalGoalEntity.getEndDate());
        responseModel.setPausedDate(personalGoalEntity.getPausedDate());
        responseModel.setGlobalGoalId(personalGoalEntity.getGlobalGoal().getId());
        responseModel.setUserId(personalGoalEntity.getUser().getId());
        return new ResponseEntity(responseModel, HttpStatus.CREATED);
    }
}
