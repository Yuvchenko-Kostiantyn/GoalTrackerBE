package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.DayProgressEntity;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Day progress repository
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 11:52
 */
@Repository
public interface DayProgressRepository extends JpaRepository<DayProgressEntity, Long> {
    List<DayProgressEntity> findByPersonalGoalId(long personalGoalId);
}
