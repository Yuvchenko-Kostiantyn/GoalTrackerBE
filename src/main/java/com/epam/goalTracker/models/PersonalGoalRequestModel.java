package com.epam.goalTracker.models;

import com.epam.goalTracker.entities.DayProgressEntity;
import com.epam.goalTracker.entities.GlobalGoalEntity;
import com.epam.goalTracker.entities.UserEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 18:55
 */
@Data
@ToString
public class PersonalGoalRequestModel {

    private String name;
    private Date startDate;
    private Date endDate;
    private long globalGoalId;
    private long userId;
}
