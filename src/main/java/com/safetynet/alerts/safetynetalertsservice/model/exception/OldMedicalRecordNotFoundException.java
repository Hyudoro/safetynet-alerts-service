package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OldMedicalRecordNotFoundException extends RuntimeException {
    public OldMedicalRecordNotFoundException(String lastName, String firstName) {
        super("Medical record with last name " + lastName + " and first name " + firstName + " not found");
    }
}
