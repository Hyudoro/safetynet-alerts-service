package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FirestationResponseDTO;

public interface FireStationService {
    FirestationResponseDTO getResidentsByStation(String stationNumber);

}
