package com.epam.goalTracker.controller;

import com.epam.goalTracker.dto.GlobalGoalDto;
import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.entities.enums.Season;
import com.epam.goalTracker.models.GlobalGoalRequestModel;
import com.epam.goalTracker.models.GlobalGoalResponseModel;
import com.epam.goalTracker.models.UserResponseModel;
import com.epam.goalTracker.service.GlobalGoalService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 13:58
 */

@RestController
@CrossOrigin
@RequestMapping("/global-goal")
public class GlobalGoalController {

    private GlobalGoalService globalGoalService;
    private ModelMapper modelMapper;

    public GlobalGoalController(GlobalGoalService globalGoalService, ModelMapper modelMapper) {
        this.globalGoalService = globalGoalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGlobalGoal(@PathVariable long id) {
        GlobalGoalDto globalGoalDto = globalGoalService.findGlobalDtoById(id);
        GlobalGoalResponseModel responseModel = modelMapper.map(globalGoalDto, GlobalGoalResponseModel.class);
        return ResponseEntity.ok(responseModel);
    }
    @GetMapping(path = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllGlobalGoals() {
        List<GlobalGoalDto> globalGoalDtoList = globalGoalService.findAllGlobalDto();
        List<GlobalGoalResponseModel> globalGoalResponseModels = globalGoalDtoList.stream()
                .map(globalGoalDto -> modelMapper.map(globalGoalDto, GlobalGoalResponseModel.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(globalGoalResponseModels);
    }

//    @GetMapping(path = "/season",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity getAllGlobalGoalsBySeason(@RequestParam String season) {
//        System.out.println(season);
//        List<GlobalGoalDto> globalGoalDtoList = globalGoalService.findAllGlobalDtoBySeason(season);
//        System.out.println(globalGoalDtoList);
//        List<GlobalGoalResponseModel> globalGoalResponseModels = globalGoalDtoList.stream()
//                .map(globalGoalDto -> modelMapper.map(globalGoalDto, GlobalGoalResponseModel.class))
//                .collect(Collectors.toList());
//        System.out.println(globalGoalResponseModels);
//        return ResponseEntity.ok(globalGoalResponseModels);
//    }

}
