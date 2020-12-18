package com.epam.goalTracker.dto;

import com.epam.goalTracker.entities.PersonalGoalEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Day progress dto
 *
 * @author Fazliddin Makhsudov
 */
@Data
@ToString
public class DayProgressDto {

    private long id;
    private Date date;
    private String url;
    private PersonalGoalEntity personalGoal;

}
