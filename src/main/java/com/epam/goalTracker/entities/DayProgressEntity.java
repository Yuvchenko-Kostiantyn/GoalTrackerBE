package com.epam.goalTracker.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Day Progress Entity
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 18.12.2020 21:15
 */

@Entity
@Table(name = "day_progresses")
@Data
@ToString
public class DayProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date date;

    @Column
    private String url;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_goals_id", referencedColumnName = "id")
    private PersonalGoalEntity personalGoal;

}
