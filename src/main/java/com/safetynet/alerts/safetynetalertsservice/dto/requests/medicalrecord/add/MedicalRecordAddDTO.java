package com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord.add;
import java.util.List;


public record MedicalRecordAddDTO(
        String firstName,
        String lastName,
        String birthDate,
        List<String> medications,
        List<String> allergies
) {}
