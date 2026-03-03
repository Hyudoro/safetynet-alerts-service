package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class OldPersonNotFoundException extends RuntimeException {
    public OldPersonNotFoundException(String lastName, String fristName) {
        super("Person with last name " + lastName + " and first name " + fristName + " not found");
    }
}
