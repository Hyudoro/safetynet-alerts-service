package com.safetynet.alerts.safetynetalertsservice.service.firestation;


public interface UpdateFireStationCommand {
    void execute(String address, String stationNumber);
}
