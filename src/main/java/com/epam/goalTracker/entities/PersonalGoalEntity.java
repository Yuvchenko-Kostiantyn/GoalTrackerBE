package com.epam.goalTracker.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Personal Goal Entity
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 18.12.2020 20:53
 */

@Entity
@Table(name = "personal_goals")
@Data
@ToString
public class PersonalGoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="pause_date")
    private Date pausedDate;

    @Column(name="is_closed")
    private boolean isClosed;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "global_goals_id", referencedColumnName = "id")
    private GlobalGoalEntity globalGoal;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "personalGoal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DayProgressEntity> dayProgresses;
}
