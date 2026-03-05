package com.safetynet.alerts.safetynetalertsservice.dto.responses.fire;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ResidentMedicalDTO(
        @NotBlank String lastName,
        @NotBlank  String phoneNumber,
        @NotBlank int age,
        @NotBlank List<String> medications,
        @NotBlank List<String> allergies
) {}
