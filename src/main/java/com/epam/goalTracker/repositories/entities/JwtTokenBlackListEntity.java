package com.epam.goalTracker.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Jwt black list entity
 *
 * @author Fazliddin Makhsudov
 */
@Entity
@Table(name = "jwtblacklist")
@Data
public class JwtTokenBlackListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String token;

    @Column(nullable = false)
    private LocalDateTime dateOfAdding;
}
