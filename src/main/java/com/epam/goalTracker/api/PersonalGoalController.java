package com.epam.goalTracker.api;

import com.epam.goalTracker.api.models.PersonalGoalRequestModel;
import com.epam.goalTracker.api.models.PersonalGoalResponseModel;
import com.epam.goalTracker.repositories.PersonalGoalStatusWrapper;
import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import com.epam.goalTracker.services.PersonalGoalService;
import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Personal goal controller
 *
 * @author Fazliddin Makhsudov
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
        requestModel.setSeason(requestModel.getSeason().toUpperCase());
        PersonalGoalDomain personalGoalDomain = modelMapper.map(requestModel, PersonalGoalDomain.class);

        personalGoalService.createPersonalGoal(requestModel.getUserid(), requestModel.getGlobalGoalid(), personalGoalDomain);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtainPersonalGoal(@RequestParam long userId,
                                                    @RequestParam long personalGoalId) {
        PersonalGoalDomain personalGoalDomain = personalGoalService.findPersonalGoal(userId, personalGoalId);
        return ResponseEntity.ok(obtainPersonalGoalResponseModel(personalGoalDomain));
    }

    @GetMapping(path = "/status",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtainPersonalGoalsByStatus(@RequestParam long userId,
                                                      @RequestParam String status) {
        List<PersonalGoalResponseModel> personalGoalResponseModels =
                personalGoalService.findPersonalGoalsByStatus(userId, status).stream()
                        .map(this::obtainPersonalGoalResponseModel)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(personalGoalResponseModels);
    }

    @GetMapping(path = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtainPersonalGoal(@RequestParam long userId) {
        List<PersonalGoalResponseModel> personalGoalResponseModels =
                personalGoalService.findAllPersonalGoals(userId).stream()
                        .map(this::obtainPersonalGoalResponseModel)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(personalGoalResponseModels);
    }

    @GetMapping(path = "/all-grouped",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtainGroupedPersonalGoal(@RequestParam long userId) {
        System.out.println(userId);
        return ResponseEntity.ok(personalGoalService.groupPersonalGoalsByStatuses(userId));
    }

    private PersonalGoalResponseModel obtainPersonalGoalResponseModel(PersonalGoalDomain personalGoalDomain) {
        return modelMapper.map(personalGoalDomain, PersonalGoalResponseModel.class);
    }
}
