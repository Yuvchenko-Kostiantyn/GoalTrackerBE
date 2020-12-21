package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.PersonalGoalNotFoundException;
import com.epam.goalTracker.exceptions.UserNotFoundException;
import com.epam.goalTracker.repositories.GlobalGoalRepository;
import com.epam.goalTracker.repositories.PersonalGoalRepository;
import com.epam.goalTracker.repositories.UserRepository;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.repositories.entities.UserEntity;
import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import com.epam.goalTracker.services.GlobalGoalService;
import com.epam.goalTracker.services.PersonalGoalService;
import com.epam.goalTracker.services.UserService;
import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Personal Goal service implementation
 *
 * @author Yevhenii Kravtsov
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 19.12.2020 18:36
 */
@Service
@Slf4j
public class PersonalGoalServiceImpl implements PersonalGoalService {

    private final PersonalGoalRepository personalGoalRepository;
    private final UserService userService;
    private final GlobalGoalService globalGoalService;
    private final ModelMapper modelMapper;
    private final GlobalGoalRepository globalGoalRepository;
    private final UserRepository userRepository;

    @Autowired
    public PersonalGoalServiceImpl(PersonalGoalRepository personalGoalRepository, UserService userService,
                                   GlobalGoalService globalGoalService, ModelMapper modelMapper,
                                   UserRepository userRepository, GlobalGoalRepository globalGoalRepository) {
        this.personalGoalRepository = personalGoalRepository;
        this.userService = userService;
        this.globalGoalService = globalGoalService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.globalGoalRepository = globalGoalRepository;
    }

    @Override
    public PersonalGoalDomain createPersonalGoal(long userId, long globalGoalId,
                                                 PersonalGoalDomain personalGoalDomain) {
        log.info("Saving a new personal goal ");
        PersonalGoalEntity personalGoalEntity = modelMapper.map(personalGoalDomain, PersonalGoalEntity.class);
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException(ErrorMessages.NO_USER_FOUND);
        } else {
            personalGoalEntity.setUser(userEntity);
            userEntity.getPersonalGoals().add(personalGoalEntity);
        }
        GlobalGoalEntity globalGoalEntity = globalGoalRepository.findById(globalGoalId).orElse(null);
        if (globalGoalEntity == null) {
            long differenceInTime = personalGoalDomain.getEndDate().getTime()
                    - personalGoalDomain.getStartDate().getTime();
            long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;
            globalGoalEntity = GlobalGoalEntity.builder()
                    .name(personalGoalDomain.getName())
                    .days(differenceInDays)
                    .ispersonal(true)
                    .description(personalGoalDomain.getDescription())
                    .season(personalGoalDomain.getSeason())
                    .personalGoals(new ArrayList<>())
                    .build();
        }
        personalGoalEntity.setStatus(PersonalGoalStatus.PLANNED);
        globalGoalEntity.getPersonalGoals().add(personalGoalEntity);
        personalGoalEntity.setGlobalGoal(globalGoalEntity);
        personalGoalRepository.save(personalGoalEntity);
        personalGoalDomain.setId(personalGoalEntity.getId());
        return personalGoalDomain;
    }

    @Override
    public PersonalGoalDomain findPersonalGoal(long userId, long personalGoalId) {
        PersonalGoalEntity personalGoalEntity =
                personalGoalRepository.findUserPersonalGoal(userId, personalGoalId);
        if (personalGoalEntity == null) {
            throw new PersonalGoalNotFoundException(ErrorMessages.NO_PERSONAL_GOAL_FOUND);
        }
        return getPersonalGoalDomain(personalGoalEntity);
    }

    @Override
    public PersonalGoalDomain deletePersonalGoal(long userId, long personalGoalId) {
        PersonalGoalEntity personalGoalEntity =
                personalGoalRepository.findUserPersonalGoal(userId, personalGoalId);
        if (personalGoalEntity == null) {
            throw new PersonalGoalNotFoundException(ErrorMessages.NO_PERSONAL_GOAL_FOUND);
        }
        personalGoalRepository.delete(personalGoalEntity);
        return getPersonalGoalDomain(personalGoalEntity);
    }

    @Override
    public List<PersonalGoalDomain> findPersonalGoalsByStatus(long userId, String goalStatus) {
        List<PersonalGoalEntity> goalList =
                personalGoalRepository.findUserPersonalGoalsByStatus(userId, goalStatus.toUpperCase());
        return goalList.stream().map(this::getPersonalGoalDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Map<PersonalGoalStatus, Integer> groupPersonalGoalsByStatuses(long userId) {
//        List<PersonalGoalStatusWrapper> statusWrappers =
//                personalGoalRepository.obtainMapOfPersonalGoalStatus(userId);
        Map<PersonalGoalStatus, Integer> goalStatusesMap = new HashMap<>();
        findAllPersonalGoals(userId).stream().forEach(item -> {
            if (goalStatusesMap.containsKey(item.getStatus())) {
                goalStatusesMap.replace(item.getStatus(), goalStatusesMap.get(item.getStatus()) + 1);
            } else {
                goalStatusesMap.put(item.getStatus(), 1);
            }
        });
        return goalStatusesMap;

    }

    @Override
    public List<PersonalGoalDomain> findAllPersonalGoals(long userId) {
        List<PersonalGoalEntity> goalList =
                personalGoalRepository.findUserPersonalGoals(userId);
        return goalList.stream().map(this::getPersonalGoalDomain)
                .collect(Collectors.toList());
    }

    private PersonalGoalDomain getPersonalGoalDomain(PersonalGoalEntity personalGoalEntity) {
        PersonalGoalDomain personalGoalDomain = modelMapper.map(personalGoalEntity, PersonalGoalDomain.class);
        personalGoalDomain.setName(personalGoalEntity.getGlobalGoal().getName());
        personalGoalDomain.setDescription(personalGoalEntity.getGlobalGoal().getDescription());
        personalGoalDomain.setSeason(personalGoalEntity.getGlobalGoal().getSeason());
        personalGoalDomain.setDays(personalGoalEntity.getGlobalGoal().getDays());
        return personalGoalDomain;
    }
}
