package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OldFireStationNotFoundException extends RuntimeException {
    public OldFireStationNotFoundException(String address, String station) {
        super("Old Fire Station not found with address " + address + " and station " + station);
    }
}
