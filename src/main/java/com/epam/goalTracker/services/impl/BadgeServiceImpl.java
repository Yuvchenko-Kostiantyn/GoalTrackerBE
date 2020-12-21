package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.GlobalGoalNotFoundException;
import com.epam.goalTracker.repositories.GlobalBadgeRepository;
import com.epam.goalTracker.repositories.GlobalGoalRepository;
import com.epam.goalTracker.repositories.PersonalBadgeRepository;
import com.epam.goalTracker.repositories.entities.GlobalBadgeEntity;
import com.epam.goalTracker.repositories.entities.PersonalBadgeEntity;
import com.epam.goalTracker.services.BadgeService;
import com.epam.goalTracker.services.domains.PersonalBadgeDomain;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final ModelMapper modelMapper;

    @Autowired
    public BadgeServiceImpl(GlobalBadgeRepository globalBadgeRepository, PersonalBadgeRepository personalBadgeRepository, ModelMapper modelMapper) {
        this.globalBadgeRepository = globalBadgeRepository;
        this.personalBadgeRepository = personalBadgeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonalBadgeDomain createBadge(PersonalBadgeDomain personalBadgeDomain) {
        GlobalBadgeEntity globalBadgeEntity =
                globalBadgeRepository.findById(personalBadgeDomain.getGlobalBadgeId()).orElse(null);
        if (globalBadgeEntity == null) {
            throw new GlobalGoalNotFoundException(ErrorMessages.NO_GLOBAL_BADGE_FOUND);
        }
        PersonalBadgeEntity personalBadgeEntity = PersonalBadgeEntity.builder()
                .globalBadge(globalBadgeEntity)
                .personalGoalId(personalBadgeDomain.getPersonalGoalId())
                .userId(personalBadgeDomain.getUserId())
                .build();
        globalBadgeEntity.getPersonalBadges().add(personalBadgeEntity);
        formPersonalBadgeDomain(personalBadgeDomain, personalBadgeEntity);
        return personalBadgeDomain;
    }

    @Override
    public PersonalBadgeDomain findBadgeDomainById(long personalGoalId, long badgeId) {
        return null;
    }

    @Override
    public List<PersonalBadgeDomain> findBabgeDomains(long personalGoalId) {
        return null;
    }

    private void formPersonalBadgeDomain(PersonalBadgeDomain personalBadgeDomain, PersonalBadgeEntity personalBadgeEntity) {
        personalBadgeEntity = personalBadgeRepository.save(personalBadgeEntity);
        personalBadgeDomain.setId(personalBadgeEntity.getId());
        personalBadgeDomain.setName(personalBadgeEntity.getGlobalBadge().getName());
        personalBadgeDomain.setUrl(personalBadgeEntity.getGlobalBadge().getUrl());
        personalBadgeDomain.setScores(personalBadgeEntity.getGlobalBadge().getScores());
    }
}
