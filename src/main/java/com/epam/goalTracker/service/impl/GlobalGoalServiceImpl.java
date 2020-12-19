package com.epam.goalTracker.service.impl;

import com.epam.goalTracker.dto.GlobalGoalDto;
import com.epam.goalTracker.dto.UserDto;
import com.epam.goalTracker.entities.GlobalGoalEntity;
import com.epam.goalTracker.entities.UserEntity;
import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.GlobalGoalNotFoundException;
import com.epam.goalTracker.exceptions.UserNotFoundException;
import com.epam.goalTracker.repository.GlobalGoalRepository;
import com.epam.goalTracker.service.GlobalGoalService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global goal service implementation
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 11:58
 */
@Service
@Slf4j
public class GlobalGoalServiceImpl implements GlobalGoalService {

    private final GlobalGoalRepository globalGoalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GlobalGoalServiceImpl(GlobalGoalRepository globalGoalRepository, ModelMapper modelMapper) {
        this.globalGoalRepository = globalGoalRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public GlobalGoalDto findGlobalDtoById(long id) {
        log.info("Find global goal by id");
        GlobalGoalEntity globalGoalEntity = globalGoalRepository.findById(id).orElse(null);
        if (globalGoalEntity == null) {
            throw new GlobalGoalNotFoundException(ErrorMessages.NO_GLOBAL_GOAL_FOUND);
        }
        return modelMapper.map(globalGoalEntity, GlobalGoalDto.class);
    }

    @Override
    public List<GlobalGoalDto> findAllGlobalDtoBySeason(String season) {
        log.info("Find global goal by season");
        return globalGoalRepository.findBySeason(season).stream()
                .map(globalGoalEntity -> modelMapper.map(globalGoalEntity, GlobalGoalDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GlobalGoalDto> findAllGlobalDto() {
        log.info("Getting a list of all global goals");
        return globalGoalRepository.findAll().stream().map(globalGoalEntity -> modelMapper.map(globalGoalEntity, GlobalGoalDto.class))
                .collect(Collectors.toList());
    }
}
