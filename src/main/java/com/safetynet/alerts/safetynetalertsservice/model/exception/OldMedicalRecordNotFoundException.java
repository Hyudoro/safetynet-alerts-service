package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class OldMedicalRecordNotFoundException extends RuntimeException {
    public OldMedicalRecordNotFoundException(String lastName, String firstName) {
        super("Medical record with last name " + lastName + " and first name " + firstName + " not found");
    }
}
