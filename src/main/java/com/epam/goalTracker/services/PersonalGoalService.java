package com.epam.goalTracker.services;

import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;

import java.util.List;
import java.util.Map;

/**
 * Personal goal service
 *
 * @author Fazliddin Makhsudov
 */

public interface PersonalGoalService {

    PersonalGoalDomain createPersonalGoal(long userId, PersonalGoalDomain personalGoalDomain);

    PersonalGoalDomain findPersonalGoal(long userId, long personalGoalId);

    PersonalGoalDomain deletePersonalGoal(long userId, long personalGoalId);

    List<PersonalGoalDomain> findPersonalGoalsByStatus(long userId, String goalStatus);

    Map<PersonalGoalStatus, Integer> findPersonalGoalsByStatus(long userId);

    List<PersonalGoalDomain> findAllPersonalGoals(long userId);
}
