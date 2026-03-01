package com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.update;

import jakarta.validation.constraints.NotBlank;

public record FireStationUpdateDTO(
        @NotBlank String address,
        @NotBlank String oldStationNumber,
        @NotBlank String newStationNumber

)
{
}
