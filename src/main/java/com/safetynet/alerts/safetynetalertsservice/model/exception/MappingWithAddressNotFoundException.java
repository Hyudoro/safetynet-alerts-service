package com.safetynet.alerts.safetynetalertsservice.model.exception;

public class MappingWithAddressNotFoundException extends RuntimeException {
    public MappingWithAddressNotFoundException(String address) {
        super("Mapping with address " + address + " not found");
    }
}
