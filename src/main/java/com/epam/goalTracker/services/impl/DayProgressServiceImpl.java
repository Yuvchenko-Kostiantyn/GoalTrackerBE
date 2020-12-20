package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.exceptions.DayProgressNotFoundException;
import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.GlobalGoalNotFoundException;
import com.epam.goalTracker.repositories.DayProgressRepository;
import com.epam.goalTracker.repositories.PersonalGoalRepository;
import com.epam.goalTracker.repositories.entities.DayProgressEntity;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.Season;
import com.epam.goalTracker.services.DayProgressService;
import com.epam.goalTracker.services.domains.DayProgressDomain;
import com.epam.goalTracker.services.domains.GlobalGoalDomain;
import com.epam.goalTracker.services.domains.PersonalGoalDomain;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Day progress service implementation
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 20.12.2020 16:58
 */
@Service
@Slf4j
public class DayProgressServiceImpl implements DayProgressService {

    private ModelMapper modelMapper;
    private PersonalGoalRepository personalGoalRepository;
    private DayProgressRepository dayProgressRepository;

    @Autowired
    public DayProgressServiceImpl(ModelMapper modelMapper, PersonalGoalRepository personalGoalRepository, DayProgressRepository dayProgressRepository) {
        this.modelMapper = modelMapper;
        this.personalGoalRepository = personalGoalRepository;
        this.dayProgressRepository = dayProgressRepository;
    }

    @Override
    public DayProgressDomain createDayProgressDto(long personalGoalId, DayProgressDomain dayProgressDomain) {
        log.info("Saving a new day Progress " + dayProgressDomain);
        PersonalGoalEntity personalGoalEntity = personalGoalRepository.findById(personalGoalId).orElse(null);
        //PersonalGoalDomain personalGoalDomain = modelMapper.map(personalGoalEntity, PersonalGoalDomain.class);
        dayProgressDomain.setPersonalGoal(personalGoalEntity);
        DayProgressEntity dayProgressEntity = modelMapper.map(dayProgressDomain, DayProgressEntity.class);
        DayProgressEntity storedDayProgress = dayProgressRepository.save(dayProgressEntity);
        return modelMapper.map(storedDayProgress, DayProgressDomain.class);
    }

    @Override
    public DayProgressDomain findDayProgressDto(long dayProgressDtoId) {
        log.info("Find day progress by id");
        DayProgressEntity dayProgressEntity = dayProgressRepository.findById(dayProgressDtoId).orElse(null);
        if (dayProgressEntity == null) {
            throw new DayProgressNotFoundException(ErrorMessages.NO_DAY_PROGRESS_FOUND);
        }
        return modelMapper.map(dayProgressEntity, DayProgressDomain.class);
    }

    @Override
    public void deleteDayProgressByID(long dayProgressId) {
        DayProgressEntity dayProgressEntity = dayProgressRepository.findById(dayProgressId).orElse(null);
        if (dayProgressEntity == null) {
            throw new DayProgressNotFoundException(ErrorMessages.NO_DAY_PROGRESS_FOUND);
        }
        dayProgressRepository.deleteById(dayProgressId);
    }

    @Override
    public List<DayProgressDomain> findAllDayProgresses(long personalGoalId) {
        log.info("Find day progresses by personal goal id");
        return dayProgressRepository.findByPersonalGoalId(personalGoalId).stream()
                .map(dayProgressEntity -> modelMapper.map(dayProgressEntity, DayProgressDomain.class))
                .collect(Collectors.toList());
    }

}
