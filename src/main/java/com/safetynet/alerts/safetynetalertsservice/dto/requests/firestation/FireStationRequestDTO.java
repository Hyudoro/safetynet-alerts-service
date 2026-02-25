package com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation;


import jakarta.validation.constraints.NotBlank;

public record FireStationRequestDTO(
       @NotBlank String address,
       @NotBlank String station
) {
}
