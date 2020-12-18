package com.epam.goalTracker.dto;

import com.epam.goalTracker.entities.DayProgressEntity;
import com.epam.goalTracker.entities.GlobalGoalEntity;
import com.epam.goalTracker.entities.UserEntity;
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
public class PersonalGoalDto {

    private long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date pausedDate;
    private boolean isClosed;
    private GlobalGoalEntity globalGoal;
    private UserEntity user;
    private List<DayProgressEntity> dayProgresses;

}
