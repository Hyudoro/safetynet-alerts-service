package com.safetynet.alerts.safetynetalertsservice.dto.responses.personinfo;

public record PersonInfoDTO(
        String firstName,
        String lastName,
        String address,
        int age,
        String email,
        String[] medications,
        String[] allergies

) {
}
