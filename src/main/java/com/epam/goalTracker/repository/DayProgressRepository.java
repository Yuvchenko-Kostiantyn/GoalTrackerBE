package com.epam.goalTracker.repository;

import com.epam.goalTracker.entities.DayProgressEntity;
import com.epam.goalTracker.entities.GlobalGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 11:52
 */
public interface DayProgressRepository extends JpaRepository<DayProgressEntity, Long> {
}
