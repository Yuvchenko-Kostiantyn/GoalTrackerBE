package com.epam.goalTracker.dto.request;

import lombok.Data;
import java.io.Serializable;
@Data
public class UserRequest implements Serializable {

    private String firstName;
    private String secondName;

    public UserRequest(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
