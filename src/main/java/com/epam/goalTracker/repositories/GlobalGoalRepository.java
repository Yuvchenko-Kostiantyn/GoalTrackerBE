package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Global goal repository
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 11:52
 */

@Repository
public interface GlobalGoalRepository extends JpaRepository<GlobalGoalEntity, Long> {

    List<GlobalGoalEntity> findBySeason(Season season);
}
