package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MappingWithStationAndAddressNotFoundException extends RuntimeException {
    public MappingWithStationAndAddressNotFoundException(String address, String station) {
        super("Mapping with station " + station + " and address " + address + " not found");
    }
}
