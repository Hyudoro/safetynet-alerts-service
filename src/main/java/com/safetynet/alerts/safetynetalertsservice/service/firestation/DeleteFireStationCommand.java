package com.safetynet.alerts.safetynetalertsservice.service.firestation;

public interface DeleteFireStationCommand {
    void executeByAddress(String address);
    void executeByStation(String station);
}
