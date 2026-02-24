package com.safetynet.alerts.safetynetalertsservice.service.firestation;

public interface DeleteFireStationCommand {
    void executeByAdress(String address);
    void executeByStation(String station);
}
