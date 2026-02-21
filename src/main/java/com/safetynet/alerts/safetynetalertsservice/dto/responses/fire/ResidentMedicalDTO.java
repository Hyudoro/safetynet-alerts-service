package com.safetynet.alerts.safetynetalertsservice.dto.responses.fire;

public record ResidentMedicalDTO(
        String firstName,
        String lastName,
        String phone,
        int age,
        String[] medications,
        String[] allergies
) {}
