package com.safetynet.alerts.safetynetalertsservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MappingWithAddressNotFoundException extends RuntimeException {
    public MappingWithAddressNotFoundException(String address) {
        super("Mapping with address " + address + " not found");
    }
}
