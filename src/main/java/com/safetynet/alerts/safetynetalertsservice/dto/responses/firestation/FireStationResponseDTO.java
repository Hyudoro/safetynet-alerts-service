package com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FireStationResponseDTO(
        List<ResidentDTO> residents,
        @JsonProperty("adultcount")
        long adultCount,
        @JsonProperty("childcount")
        long childCount)
{}

