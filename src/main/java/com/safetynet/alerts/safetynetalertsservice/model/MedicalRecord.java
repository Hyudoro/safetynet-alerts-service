package com.safetynet.alerts.safetynetalertsservice.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record MedicalRecord (
     String firstName,
     String lastName,
     @JsonProperty("birthdate")
     String birthDate,
     List<String> medications,
     List<String> allergies
)
{}

