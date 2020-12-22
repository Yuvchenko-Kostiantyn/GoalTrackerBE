package com.epam.goalTracker.api;

import com.epam.goalTracker.api.models.GlobalGoalResponseModel;
import com.epam.goalTracker.services.GlobalGoalService;
import com.epam.goalTracker.services.domains.GlobalGoalDomain;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final GlobalGoalService globalGoalService;
    private final ModelMapper modelMapper;

    public GlobalGoalController(GlobalGoalService globalGoalService, ModelMapper modelMapper) {
        this.globalGoalService = globalGoalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGlobalGoal(@PathVariable long id) {
        GlobalGoalDomain globalGoalDomain = globalGoalService.findGlobalDomainById(id);
        GlobalGoalResponseModel responseModel = modelMapper.map(globalGoalDomain, GlobalGoalResponseModel.class);
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping(path = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllGlobalGoals() {
        List<GlobalGoalDomain> globalGoalDomainList = globalGoalService.findAllGlobalDomain();
        List<GlobalGoalResponseModel> globalGoalResponseModels = globalGoalDomainList.stream()
                .map(globalGoalDomain -> modelMapper.map(globalGoalDomain, GlobalGoalResponseModel.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(globalGoalResponseModels);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllGlobalGoalsBySeason(@RequestParam String season) {
        List<GlobalGoalDomain> globalGoalDomainList = globalGoalService.findAllGlobalDomainBySeason(season);
        List<GlobalGoalResponseModel> globalGoalResponseModels = globalGoalDomainList.stream()
                .map(globalGoalDomain -> modelMapper.map(globalGoalDomain, GlobalGoalResponseModel.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(globalGoalResponseModels);
    }

}
