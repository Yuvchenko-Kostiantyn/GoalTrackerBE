package com.epam.goalTracker.api;

import com.epam.goalTracker.api.models.DayProgressRequestModel;
import com.epam.goalTracker.api.models.DayProgressResponseModel;
import com.epam.goalTracker.services.DayProgressService;
import com.epam.goalTracker.services.domains.DayProgressDomain;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Day progress controller
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 20.12.2020 16:56
 */

@RestController
@CrossOrigin
@RequestMapping("/day-progress")
public class DayProgressController {

    private final ModelMapper modelMapper;
    private final DayProgressService dayProgressService;

    @Autowired
    public DayProgressController(ModelMapper modelMapper, DayProgressService personalGoalService) {
        this.modelMapper = modelMapper;
        this.dayProgressService = personalGoalService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody DayProgressRequestModel requestDto) {

        DayProgressDomain dayProgressDomain = modelMapper.map(requestDto, DayProgressDomain.class);
        DayProgressDomain createdDayProgressDomain =
                dayProgressService.createDayProgressDto(requestDto.getPersonalGoalId(), dayProgressDomain);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDayProgress(@RequestParam long id) {
        DayProgressDomain dayProgressDomain = dayProgressService.findDayProgressDto(id);
        DayProgressResponseModel responseModel = modelMapper.map(dayProgressDomain, DayProgressResponseModel.class);
        responseModel.setPersonalGoalId(dayProgressDomain.getPersonalGoal().getId());
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllDayProgressesByPersonalGoal(@RequestParam long personalGoalId) {
        List<DayProgressDomain> dayProgressDomains = dayProgressService.findAllDayProgresses(personalGoalId);
        List<DayProgressResponseModel> dayProgressResponseModels = dayProgressDomains.stream()
                .map(dayProgressDomain -> modelMapper.map(dayProgressDomain, DayProgressResponseModel.class))
                .collect(Collectors.toList());
        dayProgressResponseModels.forEach(dayProgressResponseModel -> dayProgressResponseModel.setPersonalGoalId(personalGoalId));

        return ResponseEntity.ok(dayProgressResponseModels);
    }

    @DeleteMapping
    public ResponseEntity deleteDayProgressById(@RequestParam long id) {
        dayProgressService.deleteDayProgressByID(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
