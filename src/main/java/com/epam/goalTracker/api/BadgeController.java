package com.epam.goalTracker.api;

import com.epam.goalTracker.api.models.PersonalBadgeRequestModel;
import com.epam.goalTracker.api.models.PersonalGoalRequestModel;
import com.epam.goalTracker.services.BadgeService;
import com.epam.goalTracker.services.domains.PersonalBadgeDomain;
import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity create(@RequestBody PersonalBadgeRequestModel personalBadgeRequestModel) {
        System.out.println("s1 : " + personalBadgeRequestModel);
        badgeService.createBadge(modelMapper.map(personalBadgeRequestModel, PersonalBadgeDomain.class));

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
