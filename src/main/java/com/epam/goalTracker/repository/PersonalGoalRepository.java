package com.epam.goalTracker.repository;

import com.epam.goalTracker.entities.GlobalGoalEntity;
import com.epam.goalTracker.entities.PersonalGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 11:53
 */
@Repository
public interface PersonalGoalRepository extends JpaRepository<PersonalGoalEntity, Long> {
}
