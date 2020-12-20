package com.epam.goalTracker.repositories.entities;

import com.epam.goalTracker.repositories.entities.enums.Season;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class GlobalGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private long days;

    @Column(columnDefinition = "boolean default false")
    private Boolean personal;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Season season;

    @OneToMany(mappedBy = "globalGoal", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<PersonalGoalEntity> personalGoals;

}
