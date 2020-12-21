package com.epam.goalTracker.services.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Personal Badge domain
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 21.12.20 14:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalBadgeDomain {

    private long id;
    private String name;
    private String url;
    private long scores;
    private long globalBadgeId;
    private long personalGoalId;
    private long userId;

}
