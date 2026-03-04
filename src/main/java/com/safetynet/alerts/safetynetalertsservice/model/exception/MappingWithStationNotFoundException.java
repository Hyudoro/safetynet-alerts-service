package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MappingWithStationNotFoundException extends RuntimeException {
    public MappingWithStationNotFoundException(String station) {
        super("Mapping with station " + station + " not found");
    }
}
