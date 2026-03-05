package com.safetynet.alerts.safetynetalertsservice.model;

import java.util.List;

public record MedicalHistory(
        List<String> medications,
        List<String> allergies,
        int age
)
{
}
