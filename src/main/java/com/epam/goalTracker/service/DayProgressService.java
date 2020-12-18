package com.epam.goalTracker.service;

import com.epam.goalTracker.dto.DayProgressDto;

import java.util.List;

/**
 * Day progress service
 *
 * @author Fazliddin Makhsudov
 */
public interface DayProgressService {

    DayProgressDto createDayProgressDto(long personalGoalId, DayProgressDto dayProgressDto);

    DayProgressDto findDayProgressDto(long personalGoalId, long dayProgressDtoId);

    List<DayProgressDto> findAllDayProgresses(long personalGoalId);

}
