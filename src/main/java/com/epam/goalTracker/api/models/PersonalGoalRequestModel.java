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
public class PersonalGoalRequestModel {

    private String name;
    private String description;
    private String season;
    private Date startDate;
    private Date endDate;
    private long globalGoalid;
    private long userid;

}
