package com.safetynet.alerts.safetynetalertsservice.service;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FirestationResponseDTO;

public interface FireStationServicePort {
    FirestationResponseDTO getResidentsByStation(String stationNumber);
}
