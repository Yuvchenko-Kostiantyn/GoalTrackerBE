package com.epam.goalTracker.repository;

import com.epam.goalTracker.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User repository
 *
 * @author Yevhenii Kravtsov
 * @author Fazliddin Makhsudov
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    List<UserEntity> findByLocation(String location);

}
