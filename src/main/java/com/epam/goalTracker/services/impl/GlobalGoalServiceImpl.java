package com.epam.goalTracker.services.impl;

import com.epam.goalTracker.exceptions.UserConflictException;
import com.epam.goalTracker.repositories.entities.RoleEntity;
import com.epam.goalTracker.repositories.entities.UserEntity;
import com.epam.goalTracker.repositories.entities.enums.Role;
import com.epam.goalTracker.services.domains.GlobalGoalDomain;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.Season;
import com.epam.goalTracker.exceptions.ErrorMessages;
import com.epam.goalTracker.exceptions.GlobalGoalNotFoundException;
import com.epam.goalTracker.repositories.GlobalGoalRepository;
import com.epam.goalTracker.services.GlobalGoalService;
import com.epam.goalTracker.services.domains.UserDomain;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global goal service implementation
 *
 * @author Yevhenii Kravtsov
 * @author Fazliddin Makhsudov
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
    public GlobalGoalDomain createGlobalGoal(GlobalGoalDomain globalGoalDomain) {
        log.info("Saving a new item " + globalGoalDomain);
        GlobalGoalEntity globalGoalEntity = globalGoalRepository.findById(globalGoalDomain.getId()).orElse(null);
        if (globalGoalEntity == null) {
            globalGoalEntity = globalGoalRepository.save(modelMapper.map(globalGoalDomain, GlobalGoalEntity.class));
        }
        return modelMapper.map(globalGoalEntity, GlobalGoalDomain.class);
    }

    @Override
    public GlobalGoalDomain findGlobalDomainById(long id) {
        log.info("Find global goal by id");
        GlobalGoalEntity globalGoalEntity = globalGoalRepository.findById(id).orElse(null);
        if (globalGoalEntity == null) {
            throw new GlobalGoalNotFoundException(ErrorMessages.NO_GLOBAL_GOAL_FOUND);
        }
        return modelMapper.map(globalGoalEntity, GlobalGoalDomain.class);
    }

    @Override
    public List<GlobalGoalDomain> findAllGlobalDomainBySeason(String seasonString) {
        log.info("Find global goal by seasonString");
        return globalGoalRepository.findBySeason(Season.valueOf(seasonString.toUpperCase())).stream()
                .map(globalGoalEntity -> modelMapper.map(globalGoalEntity, GlobalGoalDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GlobalGoalDomain> findAllGlobalDomain() {
        log.info("Getting a list of all global goals");
        return globalGoalRepository.findAll().stream().map(globalGoalEntity -> modelMapper.map(globalGoalEntity, GlobalGoalDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkGlobalGoalExistence(long id) {
        return (globalGoalRepository.findById(id).orElse(null) != null);
    }
}
