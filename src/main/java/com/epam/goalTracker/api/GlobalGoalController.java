package com.epam.goalTracker.api;

import com.epam.goalTracker.services.domains.GlobalGoalDomain;
import com.epam.goalTracker.api.models.GlobalGoalResponseModel;
import com.epam.goalTracker.services.GlobalGoalService;
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

    private GlobalGoalService globalGoalService;
    private ModelMapper modelMapper;

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
        List<GlobalGoalDomain> globalGoalDomainList = globalGoalService.findAllGlobalDto();
        List<GlobalGoalResponseModel> globalGoalResponseModels = globalGoalDomainList.stream()
                .map(globalGoalDomain -> modelMapper.map(globalGoalDomain, GlobalGoalResponseModel.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(globalGoalResponseModels);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllGlobalGoalsBySeason(@RequestParam String season) {
        System.out.println(season + " 1");
        List<GlobalGoalDomain> globalGoalDomainList = globalGoalService.findAllGlobalDtoBySeason(season);
        System.out.println(globalGoalDomainList + " 2");
        List<GlobalGoalResponseModel> globalGoalResponseModels = globalGoalDomainList.stream()
                .map(globalGoalDomain -> modelMapper.map(globalGoalDomain, GlobalGoalResponseModel.class))
                .collect(Collectors.toList());
        System.out.println(globalGoalResponseModels + " 3");
        return ResponseEntity.ok(globalGoalResponseModels);
    }

}
