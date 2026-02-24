package com.safetynet.alerts.safetynetalertsservice.model;


public record MedicalRecord (
     String firstName,
     String lastName,
     String birthdate,
     String[] medications,
     String[] allergies
)
{}

