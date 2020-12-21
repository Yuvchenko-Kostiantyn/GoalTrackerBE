package com.epam.goalTracker.repositories.entities;

import com.epam.goalTracker.repositories.entities.PersonalGoalEntity;
import com.epam.goalTracker.repositories.entities.enums.Season;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Global Badge Entity
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 */

@Entity
@Table(name = "global_badges")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalBadgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private long scores;

    @OneToMany(mappedBy = "globalBadge", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<PersonalBadgeEntity> personalBadges;

}
