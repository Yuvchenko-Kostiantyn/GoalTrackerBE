package com.epam.goalTracker.models;

import com.epam.goalTracker.entities.PersonalGoalEntity;
import com.epam.goalTracker.entities.enums.Season;
import lombok.Data;
import lombok.ToString;

import java.util.List;

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
    private long days;
    private String season;

}

