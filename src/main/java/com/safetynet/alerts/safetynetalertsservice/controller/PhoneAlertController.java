package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PhoneAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.phonealert.interfaces.PhoneAlertService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phoneAlert")
@Pattern(regexp = "\\d+")
public class PhoneAlertController {
    private final PhoneAlertService service;
    private final Logger logger = LogManager.getLogger(PhoneAlertController.class);
    public PhoneAlertController(PhoneAlertService service) {
        this.service = service;
    }

    @GetMapping("/{stationNumber}")
    public PhoneAlertResponseDTO getPhonesByStation(@RequestParam @NotBlank String StationNumber) {
        logger.info("Incoming request station={} ", StationNumber);
        return service.getPhonesByStation(StationNumber);
    }
}
