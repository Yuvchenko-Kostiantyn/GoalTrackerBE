package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.exceptions.DayProgressFinishedGoalException;
import com.epam.goalTracker.exceptions.DayProgressNotFoundException;
import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.GlobalBadgeNotFoundException;
import com.epam.goalTracker.repositories.*;
import com.epam.goalTracker.repositories.entities.*;
import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import com.epam.goalTracker.services.BadgeService;
import com.epam.goalTracker.services.DayProgressService;
import com.epam.goalTracker.services.UserService;
import com.epam.goalTracker.services.domains.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Day progress service implementation
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 20.12.2020 16:58
 */
@Service
@Slf4j
public class DayProgressServiceImpl implements DayProgressService {

    private final ModelMapper modelMapper;
    private final PersonalGoalRepository personalGoalRepository;
    private final DayProgressRepository dayProgressRepository;
    private final GlobalGoalRepository globalGoalRepository;
    private final BadgeService bageService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GlobalBadgeRepository globalBadgeRepository;
    private final long GLOBAL_BADGE_ONE = 1;
    private final long GLOBAL_BADGE_TWO = 2;
    private final long GLOBAL_BADGE_THREE = 3;
    private final long GLOBAL_BADGE_FOUR = 4;


    @Autowired
    public DayProgressServiceImpl(GlobalBadgeRepository globalBadgeRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  UserService userService, UserRepository userRepository,
                                  ModelMapper modelMapper, PersonalGoalRepository personalGoalRepository,
                                  DayProgressRepository dayProgressRepository,
                                  GlobalGoalRepository globalGoalRepository, BadgeService bageService) {

        this.globalBadgeRepository = globalBadgeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.personalGoalRepository = personalGoalRepository;
        this.dayProgressRepository = dayProgressRepository;
        this.globalGoalRepository = globalGoalRepository;
        this.bageService = bageService;
    }


    @Override
    public DayProgressDomain createDayProgressDto(long personalGoalId, DayProgressDomain dayProgressDomain) {
        log.info("Saving a new day Progress " + dayProgressDomain);
        PersonalGoalEntity personalGoalEntity = personalGoalRepository.findById(personalGoalId).orElse(null);
        GlobalGoalEntity globalGoalEntity = globalGoalRepository.findById(personalGoalEntity.getGlobalGoal().getId()).orElse(null);
        UserEntity userEntity = userRepository.findById(personalGoalEntity.getUser().getId()).orElse(null);


        if (personalGoalEntity.getStatus().equals(PersonalGoalStatus.FINISHED)) {
            throw new DayProgressFinishedGoalException(ErrorMessages.FINISHED_GOAL);
        }

        dayProgressDomain.setPersonalGoal(personalGoalEntity);
        DayProgressEntity dayProgressEntity = modelMapper.map(dayProgressDomain, DayProgressEntity.class);
        DayProgressEntity storedDayProgress = dayProgressRepository.save(dayProgressEntity);

        createBadge(personalGoalEntity, userEntity);

        List<DayProgressEntity> dayProgressEntities = dayProgressRepository.findByPersonalGoalId(personalGoalId);
        personalGoalEntity.setDayProgresses(dayProgressEntities);

        if (!isFinished(personalGoalEntity)) {
            personalGoalEntity.setStatus(PersonalGoalStatus.FINISHED);
            personalGoalRepository.save(personalGoalEntity);
        }
        return modelMapper.map(storedDayProgress, DayProgressDomain.class);
    }

    @Override
    public DayProgressDomain findDayProgressDto(long dayProgressDtoId) {
        log.info("Find day progress by id");
        DayProgressEntity dayProgressEntity = dayProgressRepository.findById(dayProgressDtoId).orElse(null);
        if (dayProgressEntity == null) {
            throw new DayProgressNotFoundException(ErrorMessages.NO_DAY_PROGRESS_FOUND);
        }
        return modelMapper.map(dayProgressEntity, DayProgressDomain.class);
    }

    @Override
    public void deleteDayProgressByID(long dayProgressId) {
        DayProgressEntity dayProgressEntity = dayProgressRepository.findById(dayProgressId).orElse(null);
        if (dayProgressEntity == null) {
            throw new DayProgressNotFoundException(ErrorMessages.NO_DAY_PROGRESS_FOUND);
        }
        dayProgressRepository.deleteById(dayProgressId);
    }

    @Override
    public List<DayProgressDomain> findAllDayProgresses(long personalGoalId) {
        log.info("Find day progresses by personal goal id");
        return dayProgressRepository.findByPersonalGoalId(personalGoalId).stream()
                .map(dayProgressEntity -> modelMapper.map(dayProgressEntity, DayProgressDomain.class))
                .collect(Collectors.toList());
    }

    private boolean isFinished(PersonalGoalEntity personalGoalEntity) {
        return personalGoalEntity.getGlobalGoal().getDays() >
                personalGoalEntity.getDayProgresses().size();
    }

    private void createBadge(PersonalGoalEntity personalGoalEntity, UserEntity userEntity) {
        GlobalBadgeEntity globalBadgeEntity;
        switch (personalGoalEntity.getDayProgresses().size()) {
            case 1:
                globalBadgeEntity =  obtainGlobalBadgeEntity(GLOBAL_BADGE_ONE);
                personalGoalEntity.setStatus(PersonalGoalStatus.IN_PROGRESS);
                personalGoalRepository.save(personalGoalEntity);

                PersonalBadgeDomain personalBadgeDomain = new PersonalBadgeDomain();
                personalBadgeDomain.setGlobalBadgeId(globalBadgeEntity.getId());
                personalBadgeDomain.setPersonalGoalId(personalGoalEntity.getId());
                personalBadgeDomain.setName(globalBadgeEntity.getName());
                personalBadgeDomain.setUrl(globalBadgeEntity.getUrl());
                personalBadgeDomain.setUserId(personalGoalEntity.getUser().getId());
                personalBadgeDomain.setScores(globalBadgeEntity.getScores());

                bCryptPasswordEncoder.upgradeEncoding(userEntity.getEncryptedPassword());
                userEntity.setScores(userEntity.getScores() + personalBadgeDomain.getScores());
                userRepository.save(userEntity);

                bageService.createBadge(personalBadgeDomain);
                break;
            case 7:
                globalBadgeEntity =  obtainGlobalBadgeEntity(GLOBAL_BADGE_TWO);
                PersonalBadgeDomain personalSecondBadgeDomain = new PersonalBadgeDomain();
                personalSecondBadgeDomain.setGlobalBadgeId(globalBadgeEntity.getId());
                personalSecondBadgeDomain.setPersonalGoalId(personalGoalEntity.getId());
                personalSecondBadgeDomain.setName(globalBadgeEntity.getName());
                personalSecondBadgeDomain.setUrl(globalBadgeEntity.getUrl());
                personalSecondBadgeDomain.setUserId(personalGoalEntity.getUser().getId());
                personalSecondBadgeDomain.setScores(globalBadgeEntity.getScores());

                bCryptPasswordEncoder.upgradeEncoding(userEntity.getEncryptedPassword());
                userEntity.setScores(userEntity.getScores() + personalSecondBadgeDomain.getScores());
                userRepository.save(userEntity);

                bageService.createBadge(personalSecondBadgeDomain);
                break;
            case 14:
                globalBadgeEntity =  obtainGlobalBadgeEntity(GLOBAL_BADGE_THREE);

                PersonalBadgeDomain personalThirdBadgeDomain = new PersonalBadgeDomain();
                personalThirdBadgeDomain.setGlobalBadgeId(globalBadgeEntity.getId());
                personalThirdBadgeDomain.setPersonalGoalId(personalGoalEntity.getId());
                personalThirdBadgeDomain.setName(globalBadgeEntity.getName());
                personalThirdBadgeDomain.setUrl(globalBadgeEntity.getUrl());
                personalThirdBadgeDomain.setUserId(personalGoalEntity.getUser().getId());
                personalThirdBadgeDomain.setScores(globalBadgeEntity.getScores());

                bCryptPasswordEncoder.upgradeEncoding(userEntity.getEncryptedPassword());
                userEntity.setScores(userEntity.getScores() + personalThirdBadgeDomain.getScores());
                userRepository.save(userEntity);

                bageService.createBadge(personalThirdBadgeDomain);
                break;
            case 30:
                globalBadgeEntity =  obtainGlobalBadgeEntity(GLOBAL_BADGE_FOUR);

                PersonalBadgeDomain personalFourthBadgeDomain = new PersonalBadgeDomain();
                personalFourthBadgeDomain.setGlobalBadgeId(globalBadgeEntity.getId());
                personalFourthBadgeDomain.setPersonalGoalId(personalGoalEntity.getId());
                personalFourthBadgeDomain.setName(globalBadgeEntity.getName());
                personalFourthBadgeDomain.setUrl(globalBadgeEntity.getUrl());
                personalFourthBadgeDomain.setUserId(personalGoalEntity.getUser().getId());
                personalFourthBadgeDomain.setScores(globalBadgeEntity.getScores());

                bCryptPasswordEncoder.upgradeEncoding(userEntity.getEncryptedPassword());
                userEntity.setScores(userEntity.getScores() + personalFourthBadgeDomain.getScores());
                userRepository.save(userEntity);

                bageService.createBadge(personalFourthBadgeDomain);
                break;
            default:
                break;
        }

    }

    private GlobalBadgeEntity obtainGlobalBadgeEntity(long id) {
        GlobalBadgeEntity globalBadgeEntity = globalBadgeRepository.findById(id).orElse(null);
        if (globalBadgeEntity == null) {
            throw new GlobalBadgeNotFoundException(ErrorMessages.NO_GLOBAL_BADGE_FOUND);
        }
        return globalBadgeEntity;
    }

}
