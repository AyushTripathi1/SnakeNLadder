package com.kidex.SnakeNLadder.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public enum Status {

    SUCCESS("Success"),
    FAILURE("Failure");

    private static HashMap<String, Status> stringMap = new HashMap<>();

    static {
        for (Status operationType : Status.values()) {
            stringMap.put(operationType.type, operationType);
        }
    }

    private String type;
}
