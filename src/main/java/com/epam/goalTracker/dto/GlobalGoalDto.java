package com.epam.goalTracker.dto;

import com.epam.goalTracker.entities.PersonalGoalEntity;
import com.epam.goalTracker.entities.enums.Season;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Global goal dto
 *
 * @author Fazliddin Makhsudov
 */
@Data
@ToString
public class GlobalGoalDto {

    private long id;
    private String name;
    private long days;
    private Season season;
    private List<PersonalGoalEntity> personalGoals;

}
