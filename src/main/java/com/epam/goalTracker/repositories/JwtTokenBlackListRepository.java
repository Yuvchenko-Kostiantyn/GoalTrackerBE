package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.JwtTokenBlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JWT Token black list repository
 *
 * @author Fazliddin Makhsudov
 */
public interface JwtTokenBlackListRepository extends JpaRepository<JwtTokenBlackListEntity, Long> {

    JwtTokenBlackListEntity findByToken(String token);
}
