package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;

public interface FireStationService {
    FireStationResponseDTO getResidentsByStation(String stationNumber);
    void addFireStation(FireStation fs);
    void updateFireStation(String address, String stationNumber);
    void deleteFireStationByAddress(String address);
    void deleteFireStationByStation(String stationNumber);
}
