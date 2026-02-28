package com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.delete;

import jakarta.validation.constraints.NotBlank;

public record FireStationDeleteByStationDTO(
        @NotBlank String station
) {
}
