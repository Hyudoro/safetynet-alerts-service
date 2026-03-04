package com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert;

import jakarta.validation.constraints.NotBlank;

public record HouseHoldMemberDTO(
        @NotBlank String lastName,
        @NotBlank String firstName,
        @NotBlank String phoneNumber,
        @NotBlank String address,
        @NotBlank String city)
{
}
