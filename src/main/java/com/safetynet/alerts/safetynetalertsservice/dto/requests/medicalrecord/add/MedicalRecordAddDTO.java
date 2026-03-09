package com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord.add;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record MedicalRecordAddDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String birthDate,
        @NotNull List<@NotBlank String> medications,
        @NotNull List<@NotBlank String> allergies
) {
}
