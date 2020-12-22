package com.epam.goalTracker.services.domains;

import com.epam.goalTracker.repositories.entities.enums.Season;
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
public class GlobalGoalDomain {

    private long id;
    private String name;
    private long days;
    private Season season;
    private List<PersonalGoalDomain> personalGoals;

}
