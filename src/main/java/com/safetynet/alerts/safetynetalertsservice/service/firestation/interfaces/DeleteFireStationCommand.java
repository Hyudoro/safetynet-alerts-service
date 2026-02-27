package com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces;

public interface DeleteFireStationCommand {
    void executeByAddress(String address);
    void executeByStation(String station);
}
