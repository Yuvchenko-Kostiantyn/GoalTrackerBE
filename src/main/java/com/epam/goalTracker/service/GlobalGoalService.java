package com.epam.goalTracker.service;

import com.epam.goalTracker.dto.GlobalGoalDto;

import java.util.List;

/**
 * Global goal service
 *
 * @author Fazliddin Makhsudov
 */
public interface GlobalGoalService {

    GlobalGoalDto findGlobalDto(long id);

    List<GlobalGoalDto> findAllGlobalDto(String season);

    List<GlobalGoalDto> findAllGlobalDto();
}
