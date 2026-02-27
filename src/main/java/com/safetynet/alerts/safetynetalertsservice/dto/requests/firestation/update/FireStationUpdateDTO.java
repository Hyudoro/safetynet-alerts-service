package com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record FireStationUpdateDTO(
        @NotBlank String address,
        @JsonProperty("oldstationnumber")
        @NotBlank String oldStationNumber,
        @JsonProperty("newstationnumber")
        @NotBlank String newStationNumber

)
{
}
