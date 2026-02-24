package com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord;

import java.util.List;

public record MedicalRecordRequestDTO(
        String firstName,
        String lastName,
        String birthdate,
        List<String> medications,
        List<String> allergies
) {}
