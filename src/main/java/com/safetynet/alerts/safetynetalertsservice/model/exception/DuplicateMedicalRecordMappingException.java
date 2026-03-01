package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class DuplicateMedicalRecordMappingException extends RuntimeException {
    public DuplicateMedicalRecordMappingException(String firstName, String lastName) {
        super("Duplicate Medical Record mapping for " + firstName + " " + lastName);
    }
}
