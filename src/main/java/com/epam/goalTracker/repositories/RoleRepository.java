package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role repository
 *
 * @author Fazliddin Makhsudov
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
