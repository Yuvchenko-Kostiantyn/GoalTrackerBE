package com.epam.goalTracker.repositories;

import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import lombok.Data;

import javax.persistence.Entity;

/**
 * For making query
 * of personal goal by statuses
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 20.12.20 22:02
 */
@Entity
@Data
public class PersonalGoalStatusWrapper {

    private PersonalGoalStatus status;
    private int number;

    public PersonalGoalStatusWrapper(PersonalGoalStatus status, int number) {
        this.status = status;
        this.number = number;
    }

    public PersonalGoalStatusWrapper() {
    }

    public PersonalGoalStatus getStatus() {
        return status;
    }

    public void setStatus(PersonalGoalStatus status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
