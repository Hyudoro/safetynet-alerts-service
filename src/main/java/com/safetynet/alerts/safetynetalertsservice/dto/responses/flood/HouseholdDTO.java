package com.safetynet.alerts.safetynetalertsservice.dto.responses.flood;

import java.util.List;

public record HouseholdDTO(
    String address,
    List<FloodResidentDTO> residents
    )
{}
