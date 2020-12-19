package com.epam.goalTracker.repositories.entities;

import com.epam.goalTracker.repositories.entities.enums.Season;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Global Goal Entity
 *
 * @author Yevhenii Kravtsov
 * @version 1.0
 * @date 18.12.2020 20:44
 */

@Entity
@Table(name = "global_goals")
@Data
@ToString
@Builder
public class GlobalGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private long days;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Season season;

    @OneToMany(mappedBy = "globalGoal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalGoalEntity> personalGoals;

}
