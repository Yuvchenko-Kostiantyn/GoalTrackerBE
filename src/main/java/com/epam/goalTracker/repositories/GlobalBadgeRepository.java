package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.GlobalBadgeEntity;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Global badge repository
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 */

@Repository
public interface GlobalBadgeRepository extends JpaRepository<GlobalBadgeEntity, Long> {
}
