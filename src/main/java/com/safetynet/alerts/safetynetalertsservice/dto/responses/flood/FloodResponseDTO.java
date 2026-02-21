package com.safetynet.alerts.safetynetalertsservice.dto.responses.flood;

import java.util.List;

public record FloodResponseDTO(
    List<HouseholdDTO> households
    )
{}