package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FirestationResponseDTO;

public interface ReadFireStationCommand {
    FirestationResponseDTO getResidentsByStation(String stationNumber);
}
