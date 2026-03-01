package com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord.add;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record MedicalRecordAddDTO(
        String firstName,
        String lastName,
        String birthDate,
        List<String> medications,
        List<String> allergies
) {}
