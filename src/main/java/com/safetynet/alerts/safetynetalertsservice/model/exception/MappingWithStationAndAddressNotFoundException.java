package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class MappingWithStationAndAddressNotFoundException extends RuntimeException {
    public MappingWithStationAndAddressNotFoundException(String address, String station) {
        super("Mapping with station " + station + " and address " + address + " not found");
    }
}
