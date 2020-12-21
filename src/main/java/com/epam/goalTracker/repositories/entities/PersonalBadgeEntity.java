package com.epam.goalTracker.repositories.entities;

import com.epam.goalTracker.repositories.entities.enums.PersonalGoalStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Personal badge entity
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 */

@Entity
@Table(name = "personal_badges")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalBadgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "global_badges_id", referencedColumnName = "id")
    private GlobalBadgeEntity globalBadge;

    @Column(name = "personal_goal_id")
    private long personalGoalId;

    @Column(name = "user_id")
    private long userId;

}
