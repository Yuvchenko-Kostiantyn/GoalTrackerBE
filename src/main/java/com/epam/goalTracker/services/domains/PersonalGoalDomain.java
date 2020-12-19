package com.epam.goalTracker.services.domains;

import com.epam.goalTracker.repositories.entities.DayProgressEntity;
import com.epam.goalTracker.repositories.entities.GlobalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Personal goal dto
 *
 * @author Fazliddin Makhsudov
 */
@Data
@ToString
public class PersonalGoalDomain {

    private long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date pausedDate;
    private PersonalGoalStatus status;
    private GlobalGoalEntity globalGoal;
    private UserDomain userDomain;
    private List<DayProgressEntity> dayProgresses;

}
