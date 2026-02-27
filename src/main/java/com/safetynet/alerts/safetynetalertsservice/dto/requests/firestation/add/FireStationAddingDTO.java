package com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.add;


import jakarta.validation.constraints.NotBlank;

public record FireStationAddingDTO(
       @NotBlank String address,
       @NotBlank String station
) {
}
