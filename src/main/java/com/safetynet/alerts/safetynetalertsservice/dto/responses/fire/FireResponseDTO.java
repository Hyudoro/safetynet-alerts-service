package com.safetynet.alerts.safetynetalertsservice.dto.responses.fire;

import java.util.List;

public record FireResponseDTO(
        List<ResidentMedicalDTO> residents,
        List<String> stationNumber //Because an address can covered by multiple stations.
) {
}
