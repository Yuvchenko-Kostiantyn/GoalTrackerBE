package com.epam.goalTracker.api.models;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Day progress response model
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 20.12.2020 16:48
 */
@Data
@ToString
public class DayProgressResponseModel {

    private long id;
    private Date date;
    private String url;
    private long personalGoalId;
}
