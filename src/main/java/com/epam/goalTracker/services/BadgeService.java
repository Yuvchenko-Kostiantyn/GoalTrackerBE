package com.epam.goalTracker.services;

import com.epam.goalTracker.services.domains.PersonalBadgeDomain;

import java.util.List;

/**
 * Badge service
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 21.12.20 14:29
 */
public interface BadgeService {

    PersonalBadgeDomain createBadge(PersonalBadgeDomain personalBadgeDomain);

    PersonalBadgeDomain findBadgeDomainById(long personalGoalId, long badgeId);

    List<PersonalBadgeDomain> findBabgeDomainsByPersonalGoalId(long personalGoalId);

    List<PersonalBadgeDomain> findBabgeDomainsByUserId(long userId);
}
