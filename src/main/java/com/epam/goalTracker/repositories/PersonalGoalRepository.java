package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Personal goal repository
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 19.12.2020 11:53
 */
@Repository
public interface PersonalGoalRepository extends JpaRepository<PersonalGoalEntity, Long> {

    @Query(
            value = "SELECT * FROM personal_goals p WHERE p.users_id = ?1 and p.status = ?2",
            nativeQuery = true)
    List<PersonalGoalEntity> findUserPersonalGoalsByStatus(long userId, String status);

    @Query(
            value = "SELECT * FROM personal_goals p WHERE p.users_id = ?1",
            nativeQuery = true)
    List<PersonalGoalEntity> findUserPersonalGoals(long userId);

    @Query(
            value = "SELECT * FROM personal_goals p WHERE p.users_id = ?1 and p.id = ?2",
            nativeQuery = true)
    PersonalGoalEntity findUserPersonalGoal(long userId, long personalGoalId);

    @Query(
            value = "SELECT p.status, count(p.status) FROM personal_goals p where p.users_id = ?1 group by p.status",
//            value = "select com.epam.goalTracker.repositories.PersonalGoalStatusWrapper(p.status, "
//                    + "count(p.status) "
//                    + "FROM personal_goals p where p.users_id = ?1 group by p.status",
            nativeQuery = true)
    List<PersonalGoalStatusWrapper> obtainMapOfPersonalGoalStatus(long userId);

}
