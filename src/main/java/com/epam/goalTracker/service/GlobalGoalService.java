package com.epam.goalTracker.service;

import com.epam.goalTracker.dto.GlobalGoalDto;

import java.util.List;

/**
 * Global goal service
 *
 * @author Fazliddin Makhsudov
 */
public interface GlobalGoalService {

    GlobalGoalDto findGlobalDtoById(long id);

    List<GlobalGoalDto> findAllGlobalDtoBySeason(String season);

    List<GlobalGoalDto> findAllGlobalDto();
}
