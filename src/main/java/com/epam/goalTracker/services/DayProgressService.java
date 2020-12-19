package com.epam.goalTracker.services;

import com.epam.goalTracker.services.domains.DayProgressDomain;

import java.util.List;

/**
 * Day progress service
 *
 * @author Fazliddin Makhsudov
 */
public interface DayProgressService {

    DayProgressDomain createDayProgressDto(long personalGoalId, DayProgressDomain dayProgressDomain);

    DayProgressDomain findDayProgressDto(long personalGoalId, long dayProgressDtoId);

    List<DayProgressDomain> findAllDayProgresses(long personalGoalId);

}
