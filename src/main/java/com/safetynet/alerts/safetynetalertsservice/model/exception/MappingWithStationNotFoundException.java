package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class MappingWithStationNotFoundException extends RuntimeException {
    public MappingWithStationNotFoundException(String station) {
        super("Mapping with station " + station + " not found");
    }
}
