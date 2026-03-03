package com.safetynet.alerts.safetynetalertsservice.dto.requests.person;

import jakarta.validation.constraints.NotBlank;

public record PersonAddDTO(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String address,
    @NotBlank String city,
    @NotBlank String zip,
    @NotBlank String phone,
    @NotBlank String email)


{
}
