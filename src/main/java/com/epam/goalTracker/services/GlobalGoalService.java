package com.epam.goalTracker.services;

import com.epam.goalTracker.services.domains.GlobalGoalDomain;

import java.util.List;

/**
 * Global goal service
 *
 * @author Fazliddin Makhsudov
 */
public interface GlobalGoalService {

    GlobalGoalDomain createGlobalGoal(GlobalGoalDomain globalGoalDomain);

    GlobalGoalDomain findGlobalDomainById(long id);

    List<GlobalGoalDomain> findAllGlobalDomainBySeason(String season);

    List<GlobalGoalDomain> findAllGlobalDomain();

    boolean checkGlobalGoalExistence(long id);
}
