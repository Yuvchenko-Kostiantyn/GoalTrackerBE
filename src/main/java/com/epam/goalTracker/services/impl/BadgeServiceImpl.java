package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.GlobalGoalNotFoundException;
import com.epam.goalTracker.exceptions.PersonalBadgeNotFoundException;
import com.epam.goalTracker.exceptions.PersonalGoalNotFoundException;
import com.epam.goalTracker.repositories.GlobalBadgeRepository;
import com.epam.goalTracker.repositories.PersonalBadgeRepository;
import com.epam.goalTracker.repositories.entities.GlobalBadgeEntity;
import com.epam.goalTracker.repositories.entities.PersonalBadgeEntity;
import com.epam.goalTracker.services.BadgeService;
import com.epam.goalTracker.services.PersonalGoalService;
import com.epam.goalTracker.services.domains.PersonalBadgeDomain;
import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Badge service implementation
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 21.12.20 15:40
 */
@Service
@Slf4j
public class BadgeServiceImpl implements BadgeService {

    private final GlobalBadgeRepository globalBadgeRepository;
    private final PersonalBadgeRepository personalBadgeRepository;
    private final PersonalGoalService personalGoalService;
    private final ModelMapper modelMapper;

    @Autowired
    public BadgeServiceImpl(GlobalBadgeRepository globalBadgeRepository,
                            PersonalBadgeRepository personalBadgeRepository,
                            PersonalGoalService personalGoalService, ModelMapper modelMapper) {
        this.globalBadgeRepository = globalBadgeRepository;
        this.personalBadgeRepository = personalBadgeRepository;
        this.personalGoalService = personalGoalService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonalBadgeDomain createBadge(PersonalBadgeDomain personalBadgeDomain) {
        GlobalBadgeEntity globalBadgeEntity = obtainGlobalBadgeEntity(personalBadgeDomain.getGlobalBadgeId());
        PersonalBadgeEntity personalBadgeEntity = buildPersonalBadgeEntity(personalBadgeDomain, globalBadgeEntity);
        globalBadgeEntity.getPersonalBadges().add(personalBadgeEntity);
        formPersonalBadgeDomain(personalBadgeDomain, personalBadgeEntity);
        return personalBadgeDomain;
    }

    @Override
    public PersonalBadgeDomain findBadgeDomainById(long personalGoalId, long badgeId) {
        PersonalBadgeEntity personalBadgeEntity = obtainPersonalBadgeEntity(badgeId);
        return buildPersonalBadgeDomainForPersonalGoal(personalGoalId, personalBadgeEntity.getPersonalGoalId(),
                personalBadgeEntity.getUserId(), personalBadgeEntity);
    }

    @Override
    public List<PersonalBadgeDomain> findBabgeDomainsByPersonalGoalId(long personalGoalId) {
        List<PersonalBadgeEntity> personalBadgeEntities =
                personalBadgeRepository.findByPersonalGoalId(personalGoalId);
        return personalBadgeEntities.stream()
                .map(item -> buildPersonalBadgeDomainForPersonalGoal(personalGoalId, item.getPersonalGoalId(),
                        item.getUserId(), item))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonalBadgeDomain> findBabgeDomainsByUserId(long userId) {
        List<PersonalBadgeEntity> personalBadgeEntities =
                personalBadgeRepository.findByUserId(userId);
        return personalBadgeEntities.stream()
                .map(item -> buildPersonalBadgeDomainForPersonalGoal(item.getPersonalGoalId(),
                        item.getPersonalGoalId(), item.getUserId(), item))
                .collect(Collectors.toList());
    }

    private PersonalBadgeDomain buildPersonalBadgeDomainForPersonalGoal(long personalGoalId, long personalGoalIdOfEntity,
                                                                        long userId, PersonalBadgeEntity personalBadgeEntity) {
        comparePersonalGoalIdWithOriginal(personalGoalId, personalGoalIdOfEntity);
        PersonalGoalDomain personalGoalDomain =
                personalGoalService.findPersonalGoal(userId, personalGoalId);
        GlobalBadgeEntity globalBadgeEntity = obtainGlobalBadgeEntity(personalBadgeEntity.getGlobalBadge().getId());
        String personalGoalName = personalGoalDomain.getName();
        long globalBadgeEntityId = globalBadgeEntity.getId();
        PersonalBadgeDomain personalBadgeDomain = PersonalBadgeDomain.builder()
                .personalGoalId(personalGoalId)
                .personalGoalName(personalGoalName)
                .globalBadgeId(globalBadgeEntityId)
                .userId(userId)
                .build();
        formPersonalBadgeDomain(personalBadgeDomain, personalBadgeEntity);
        return personalBadgeDomain;
    }

    private void formPersonalBadgeDomain(PersonalBadgeDomain personalBadgeDomain, PersonalBadgeEntity personalBadgeEntity) {
        personalBadgeEntity = personalBadgeRepository.save(personalBadgeEntity);
        personalBadgeDomain.setId(personalBadgeEntity.getId());
        personalBadgeDomain.setName(personalBadgeEntity.getGlobalBadge().getName());
        personalBadgeDomain.setUrl(personalBadgeEntity.getGlobalBadge().getUrl());
        personalBadgeDomain.setScores(personalBadgeEntity.getGlobalBadge().getScores());
    }

    private GlobalBadgeEntity obtainGlobalBadgeEntity(long globalBadgeId) {
        GlobalBadgeEntity globalBadgeEntity =
                globalBadgeRepository.findById(globalBadgeId).orElse(null);
        if (globalBadgeEntity == null) {
            throw new GlobalGoalNotFoundException(ErrorMessages.NO_GLOBAL_BADGE_FOUND);
        }
        return globalBadgeEntity;
    }

    private PersonalBadgeEntity buildPersonalBadgeEntity(PersonalBadgeDomain personalBadgeDomain, GlobalBadgeEntity globalBadgeEntity) {
        PersonalBadgeEntity personalBadgeEntity = PersonalBadgeEntity.builder()
                .globalBadge(globalBadgeEntity)
                .personalGoalId(personalBadgeDomain.getPersonalGoalId())
                .userId(personalBadgeDomain.getUserId())
                .build();
        return personalBadgeEntity;
    }

    private PersonalBadgeEntity obtainPersonalBadgeEntity(long badgeId) {
        PersonalBadgeEntity personalBadgeEntity =
                personalBadgeRepository.findById(badgeId).orElse(null);
        if (personalBadgeEntity == null) {
            throw new PersonalBadgeNotFoundException(ErrorMessages.NO_PERSONAL_BADGE_FOUND);
        }
        return personalBadgeEntity;
    }

    private void comparePersonalGoalIdWithOriginal(long personalGoalId, long personalGoalIdOfEntity) {
        if (personalGoalId != personalGoalIdOfEntity) {
            throw new PersonalGoalNotFoundException(ErrorMessages.NO_PERSONAL_GOAL_FOUND);
        }
    }
}
