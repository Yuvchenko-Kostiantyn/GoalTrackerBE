package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Personal goal repository
 *
 * @author Yevhenii Kravtsov
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 19.12.2020 11:53
 */
@Repository
public interface PersonalGoalRepository extends JpaRepository<PersonalGoalEntity, Long> {

    @Query(
            value = "SELECT * FROM personal_goals p WHERE p.users_id = ?1 and p.status = ?2",
            nativeQuery = true)
    List<PersonalGoalEntity> findUserByStatusNative(long userId, String status);

    @Query(
            value = "SELECT * FROM personal_goals p WHERE p.users_id = ?1",
            nativeQuery = true)
    List<PersonalGoalEntity> findUserPersonalGoals(long userId);
}
