package com.epam.goalTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.epam.goalTracker.entities")
@SpringBootApplication
public class GoalTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoalTrackerApplication.class, args);
	}

}
