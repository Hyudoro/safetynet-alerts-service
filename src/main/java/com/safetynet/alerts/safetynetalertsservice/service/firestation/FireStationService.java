package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;

public interface FireStationService {
    FireStationResponseDTO getResidentsByStation(String stationNumber);

}
