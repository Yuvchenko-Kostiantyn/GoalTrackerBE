package com.epam.goalTracker.api.models;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Day progress request model
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 20.12.2020 16:48
 */

@Data
@ToString
public class DayProgressRequestModel {

    private Date date;
    private String url;
    private long personalGoalid;

}
