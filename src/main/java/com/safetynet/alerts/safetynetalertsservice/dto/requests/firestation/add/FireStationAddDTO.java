package com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.add;


import jakarta.validation.constraints.NotBlank;

public record FireStationAddDTO(
       @NotBlank String address,
       @NotBlank String station
) {
}
