package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePersonMappingException extends RuntimeException {
    public DuplicatePersonMappingException(String lastName, String firstName)
    {
        super("Person with last name " + lastName + " and first name " + firstName + " already exists");
    }
}
