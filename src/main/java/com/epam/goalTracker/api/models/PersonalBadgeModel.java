package com.epam.goalTracker.api.models;

import lombok.Data;
import lombok.ToString;

/**
 * Personal Badge domain
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 21.12.20 14:30
 */
@Data
@ToString
public class PersonalBadgeModel {

    private String name;
    private String url;
    private long scores;
    private long globalBadgeId;
    private long personalGoalId;
    private String personalGoalName;
    private long userId;

}
