package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.UserNotFoundException;
import com.epam.goalTracker.repositories.GlobalGoalRepository;
import com.epam.goalTracker.repositories.PersonalGoalRepository;
import com.epam.goalTracker.repositories.UserRepository;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.repositories.entities.UserEntity;
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

import java.util.ArrayList;
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
    private GlobalGoalRepository globalGoalRepository;
    private UserRepository userRepository;

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
                    .personal(true)
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
