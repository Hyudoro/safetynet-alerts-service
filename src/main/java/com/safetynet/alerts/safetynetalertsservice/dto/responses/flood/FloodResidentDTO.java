package com.safetynet.alerts.safetynetalertsservice.dto.responses.flood;

import java.util.List;

public record FloodResidentDTO(
        String firstName,
        String lastName,
        String phone,
        int age,
        List<String> medications,
        List<String> allergies
)
{
}
