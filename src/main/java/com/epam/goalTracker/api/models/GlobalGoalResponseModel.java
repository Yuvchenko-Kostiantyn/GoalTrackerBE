package com.epam.goalTracker.api.models;

import lombok.Data;
import lombok.ToString;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 14:05
 */

@Data
@ToString
public class GlobalGoalResponseModel {

    private long id;
    private String name;
    private String description;
    private long days;
    private String season;

}

