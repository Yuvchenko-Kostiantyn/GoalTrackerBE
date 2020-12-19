package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.DayProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Day progress repository
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 11:52
 */
public interface DayProgressRepository extends JpaRepository<DayProgressEntity, Long> {
}
