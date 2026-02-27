package com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;

public interface ReadFireStationCommand {
    FireStationResponseDTO getResidentsByStation(String stationNumber);
}
