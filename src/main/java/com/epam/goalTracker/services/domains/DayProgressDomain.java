package com.epam.goalTracker.services.domains;

import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
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
public class DayProgressDomain {

    private long id;
    private Date date;
    private String url;
    private PersonalGoalEntity personalGoal;

}
