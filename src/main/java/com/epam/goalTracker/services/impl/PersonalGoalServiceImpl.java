package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.repositories.PersonalGoalRepository;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import com.epam.goalTracker.repositories.entities.enums.Season;
import com.epam.goalTracker.services.GlobalGoalService;
import com.epam.goalTracker.services.PersonalGoalService;
import com.epam.goalTracker.services.UserService;
import com.epam.goalTracker.services.domains.GlobalGoalDomain;
import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import com.epam.goalTracker.services.domains.UserDomain;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    private PersonalGoalRepository personalGoalRepository;
    private UserService userService;
    private GlobalGoalService globalGoalService;
    private ModelMapper modelMapper;

    @Autowired
    public PersonalGoalServiceImpl(PersonalGoalRepository personalGoalRepository, UserService userService,
                                   GlobalGoalService globalGoalService, ModelMapper modelMapper) {
        this.personalGoalRepository = personalGoalRepository;
        this.userService = userService;
        this.globalGoalService = globalGoalService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonalGoalDomain createPersonalGoal(long userId, long globalGoalId, PersonalGoalDomain personalGoalDomain) {
        log.info("Saving a new personal goal " + personalGoalDomain);
        UserDomain userDomain = userService.findUserById(userId);
        GlobalGoalDomain globalGoalDomain;
        if (globalGoalService.checkGlobalGoalExistence(globalGoalId)) {
            System.out.println("serv 1");
            globalGoalDomain = globalGoalService.findGlobalDomainById(globalGoalId);
            System.out.println("serv 2 " + globalGoalDomain);
        } else {
            long differenceInTime = personalGoalDomain.getEndDate().getTime()
                    - personalGoalDomain.getStartDate().getTime();
            long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;
            GlobalGoalEntity globalGoalEntity = GlobalGoalEntity.builder()
                    .name(personalGoalDomain.getName())
                    .days(differenceInDays)
                    .season(Season.ALL_YEAR)
                    .build();
            globalGoalDomain = globalGoalService.createGlobalGoal(modelMapper.map(globalGoalEntity, GlobalGoalDomain.class));
        }
        personalGoalDomain.setUserDomain(userDomain);
        personalGoalDomain.setGlobalGoal(globalGoalDomain);
        PersonalGoalEntity personalGoalEntity = modelMapper.map(personalGoalDomain, PersonalGoalEntity.class);
        PersonalGoalEntity storedPersonalGoal = personalGoalRepository.save(personalGoalEntity);
        return modelMapper.map(storedPersonalGoal, PersonalGoalDomain.class);
    }

    @Override
    public PersonalGoalDomain findPersonalGoal(long userId, long personalGoalId) {
        return null;
    }

    @Override
    public PersonalGoalDomain deletePersonalGoal(long userId, long personalGoalId) {
        return null;
    }

    @Override
    public List<PersonalGoalDomain> findPersonalGoalsByStatus(long userId, String goalStatus) {
        return null;
    }

    @Override
    public Map<PersonalGoalStatus, Integer> findPersonalGoalsByStatus(long userId) {
        return null;
    }

    @Override
    public List<PersonalGoalDomain> findAllPersonalGoals(long userId) {
        return null;
    }
}
