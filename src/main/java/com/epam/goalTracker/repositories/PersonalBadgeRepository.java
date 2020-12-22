package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.PersonalBadgeEntity;
import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Personal badge repository
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 */
@Repository
public interface PersonalBadgeRepository extends JpaRepository<PersonalBadgeEntity, Long> {

    List<PersonalBadgeEntity> findByPersonalGoalId(long personalGoalId);

    List<PersonalBadgeEntity> findByUserId(long userId);

}
