package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class DuplicatePersonMappingException extends RuntimeException {
    public DuplicatePersonMappingException(String lastName, String firstName)
    {
        super("Person with last name " + lastName + " and first name " + firstName + " already exists");
    }
}
