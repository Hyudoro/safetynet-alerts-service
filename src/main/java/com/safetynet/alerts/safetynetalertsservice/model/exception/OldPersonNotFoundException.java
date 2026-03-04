package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OldPersonNotFoundException extends RuntimeException {
    public OldPersonNotFoundException(String lastName, String fristName) {
        super("Person with last name " + lastName + " and first name " + fristName + " not found");
    }
}
