package com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.FireStation;

public interface DeleteFireStationCommand {
    void executeByAddress(String address);
    void executeByStation(String station);
    void executeByFireStation(FireStation fireStation);
}
