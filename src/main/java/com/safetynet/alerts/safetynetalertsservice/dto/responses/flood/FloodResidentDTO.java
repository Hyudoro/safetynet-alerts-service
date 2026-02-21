package com.safetynet.alerts.safetynetalertsservice.dto.responses.flood;

public record FloodResidentDTO(
        String firstName,
        String lastName,
        String phone,
        int age,
        String[] medications,
        String[] allergies
)
{
}
