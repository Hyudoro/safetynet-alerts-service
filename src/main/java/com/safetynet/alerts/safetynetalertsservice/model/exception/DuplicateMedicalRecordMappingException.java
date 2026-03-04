package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateMedicalRecordMappingException extends RuntimeException {
    public DuplicateMedicalRecordMappingException(String firstName, String lastName) {
        super("Duplicate Medical Record mapping for " + firstName + " " + lastName);
    }
}
