package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import com.epam.goalTracker.services.domains.UserDomain;
import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.repositories.entities.UserEntity;
import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.UserNotFoundException;
import com.epam.goalTracker.repositories.GlobalGoalRepository;
import com.epam.goalTracker.repositories.PersonalGoalRepository;
import com.epam.goalTracker.repositories.UserRepository;
import com.epam.goalTracker.services.PersonalGoalService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 18:36
 */
@Service
@Slf4j
public class PersonalGoalServiceImpl implements PersonalGoalService{

    private PersonalGoalService personalGoalService;
    private PersonalGoalRepository personalGoalRepository;
    private UserRepository userRepository;
    private GlobalGoalRepository globalGoalRepository;
    private ModelMapper modelMapper;

    @Override
    public PersonalGoalDomain createPersonalGoal(long userId, PersonalGoalDomain personalGoalDomain) {
        log.info("Saving a new personal goal " + personalGoalDomain);
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException(ErrorMessages.NO_USER_FOUND);
        }
        personalGoalDomain.setUserDomain(modelMapper.map(userEntity, UserDomain.class));
        System.out.println(personalGoalDomain + "   service 1");
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
