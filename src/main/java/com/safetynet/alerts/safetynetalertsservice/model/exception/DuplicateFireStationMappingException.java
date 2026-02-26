package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateFireStationMappingException extends RuntimeException {
    public DuplicateFireStationMappingException(String address, String station) {
        super("Fire station mapping already exists for address : " + address + " and station : "+ station);
    }
}
