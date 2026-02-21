package com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation;

import java.util.List;

public record FirestationResponseDTO(
        List<ResidentDTO> residents,
        long adultCount,
        long childCount)
{}

