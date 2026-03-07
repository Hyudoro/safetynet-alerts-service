package com.safetynet.alerts.safetynetalertsservice.service.flood.interfaces;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResponseDTO;

import java.util.List;

public interface FloodService {
    FloodResponseDTO getHouseHoldsUnderStations(List<String> stations);
}
