package com.epam.goalTracker.services;

import com.epam.goalTracker.services.domains.GlobalGoalDomain;

import java.util.List;

/**
 * Global goal service
 *
 * @author Fazliddin Makhsudov
 */
public interface GlobalGoalService {

    GlobalGoalDomain findGlobalDtoById(long id);

    List<GlobalGoalDomain> findAllGlobalDtoBySeason(String season);

    List<GlobalGoalDomain> findAllGlobalDto();
}
