package com.epam.goalTracker.service;

import com.epam.goalTracker.dto.PersonalGoalDto;
import com.epam.goalTracker.entities.enums.PersonalGoalStatus;

import java.util.List;
import java.util.Map;

/**
 * Personal goal service
 *
 * @author Fazliddin Makhsudov
 */

public interface PersonalGoalService {

    PersonalGoalDto createPersonalGoal(PersonalGoalDto personalGoalDto);

    PersonalGoalDto findPersonalGoal(long userId, long personalGoalId);

    PersonalGoalDto deletePersonalGoal(long userId, long personalGoalId);

    List<PersonalGoalDto> findPersonalGoalsByStatus(long id, String goalStatus);

    Map<PersonalGoalStatus, Integer> findPersonalGoalsByStatus(long id);

    List<PersonalGoalDto> findAllPersonalGoals(long id);
}
