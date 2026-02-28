package com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;

public interface FireStationService {
    FireStationResponseDTO getResidentsByStation(String stationNumber);
    void addFireStation(FireStation fs);
    void updateFireStation(FireStation address, Integer stationNumber);
    void deleteMappingsByAddress(String address);
    void deleteMappingsByStation(String stationNumber);
    void deleteMapping(FireStation fs);
}
