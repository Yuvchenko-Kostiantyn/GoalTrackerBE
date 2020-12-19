package com.epam.goalTracker.service.impl;

import com.epam.goalTracker.dto.PersonalGoalDto;
import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.entities.PersonalGoalEntity;
import com.epam.goalTracker.entities.RoleEntity;
import com.epam.goalTracker.entities.UserEntity;
import com.epam.goalTracker.entities.enums.PersonalGoalStatus;
import com.epam.goalTracker.entities.enums.Role;
import com.epam.goalTracker.exceptions.UserConflictException;
import com.epam.goalTracker.repository.PersonalGoalRepository;
import com.epam.goalTracker.service.PersonalGoalService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
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
    private ModelMapper modelMapper;

    @Override
    public PersonalGoalDto createPersonalGoal(PersonalGoalDto personalGoalDto) {
        log.info("Saving a new personal goal " + personalGoalDto);
        PersonalGoalEntity personalGoalEntity = modelMapper.map(personalGoalDto, PersonalGoalEntity.class);
        PersonalGoalEntity storedPersonalGoal = personalGoalRepository.save(personalGoalEntity);
        return modelMapper.map(storedPersonalGoal, PersonalGoalDto.class);
    }

    @Override
    public PersonalGoalDto findPersonalGoal(long userId, long personalGoalId) {
        return null;
    }

    @Override
    public PersonalGoalDto deletePersonalGoal(long userId, long personalGoalId) {
        return null;
    }

    @Override
    public List<PersonalGoalDto> findPersonalGoalsByStatus(long id, String goalStatus) {
        return null;
    }

    @Override
    public Map<PersonalGoalStatus, Integer> findPersonalGoalsByStatus(long id) {
        return null;
    }

    @Override
    public List<PersonalGoalDto> findAllPersonalGoals(long id) {
        return null;
    }
}
