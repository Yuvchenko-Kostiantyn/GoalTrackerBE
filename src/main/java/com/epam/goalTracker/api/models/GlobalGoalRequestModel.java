package com.epam.goalTracker.api.models;

import lombok.Data;
import lombok.ToString;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 15:53
 */
@Data
@ToString
public class GlobalGoalRequestModel {
    private String season;
}
