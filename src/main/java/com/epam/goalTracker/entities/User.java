package com.epam.goalTracker.entities;

import lombok.Data;
import com.epam.goalTracker.entities.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

}
