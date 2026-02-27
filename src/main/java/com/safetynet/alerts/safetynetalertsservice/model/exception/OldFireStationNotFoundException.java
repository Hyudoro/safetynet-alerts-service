package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class OldFireStationNotFoundException extends RuntimeException {
    public OldFireStationNotFoundException(String address, String station) {
        super("Old Fire Station not found with address " + address + " and station " + station);
    }
}
