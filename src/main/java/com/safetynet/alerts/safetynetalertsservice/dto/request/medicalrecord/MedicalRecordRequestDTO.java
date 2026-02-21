package com.safetynet.alerts.safetynetalertsservice.dto.request.medicalrecord;

import java.util.List;

public record MedicalRecordRequestDTO(
        String firstName,
        String lastName,
        String birthdate,
        List<String> medications,
        List<String> allergies
) {}
