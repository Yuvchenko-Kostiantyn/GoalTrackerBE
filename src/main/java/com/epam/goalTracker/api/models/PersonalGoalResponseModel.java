package com.epam.goalTracker.api.models;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 19.12.2020 18:55
 */
@Data
@ToString
public class PersonalGoalResponseModel {
    private long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date pausedDate;
    private String status;
    private long globalGoalId;
    private long userId;
}
