package com.epam.goalTracker.api;

import com.epam.goalTracker.api.models.PersonalBadgeModel;
import com.epam.goalTracker.services.BadgeService;
import com.epam.goalTracker.services.domains.PersonalBadgeDomain;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Badge controller
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 21.12.20 15:53
 */
@RestController
@CrossOrigin
@RequestMapping("/badge")
public class BadgeController {

    private final ModelMapper modelMapper;
    private final BadgeService badgeService;

    @Autowired
    public BadgeController(ModelMapper modelMapper, BadgeService badgeService) {
        this.modelMapper = modelMapper;
        this.badgeService = badgeService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody PersonalBadgeModel personalBadgeModel) {
        badgeService.createBadge(modelMapper.map(personalBadgeModel, PersonalBadgeDomain.class));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtainPersonalBadge(@RequestParam long personalGoalId,
                                              @RequestParam long badgeId) {
        PersonalBadgeDomain personalBadgeDomain = badgeService.findBadgeDomainById(personalGoalId, badgeId);
        return ResponseEntity.ok(modelMapper.map(personalBadgeDomain, PersonalBadgeModel.class));
    }

    @GetMapping(path = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtainPersonalBadgesByPersonalGoal(@RequestParam long personalGoalId) {
        List<PersonalBadgeModel> personalGoalResponseModels =
                badgeService.findBabgeDomainsByPersonalGoalId(personalGoalId).stream()
                        .map(item -> modelMapper.map(item, PersonalBadgeModel.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(personalGoalResponseModels);
    }

    @GetMapping(path = "/all/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtainPerusonalBadgesByUserId(@PathVariable long userId) {
        List<PersonalBadgeModel> personalGoalResponseModels =
                badgeService.findBabgeDomainsByUserId(userId).stream()
                        .map(item -> modelMapper.map(item, PersonalBadgeModel.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(personalGoalResponseModels);
    }
}
