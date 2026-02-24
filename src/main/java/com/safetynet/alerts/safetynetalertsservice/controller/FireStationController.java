package com.safetynet.alerts.safetynetalertsservice.controller;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FirestationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.FireStationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final FireStationServiceImpl service;

    private static final Logger logger = LogManager.getLogger(FireStationController.class);

    public FireStationController(FireStationServiceImpl service)
    {
        this.service = service;
    }

    @GetMapping
    public FirestationResponseDTO getByStation(@RequestParam String stationNumber) {

        logger.info("Incoming request station={}", stationNumber);
        FirestationResponseDTO response = service.getResidentsByStation(stationNumber);
        logger.info("Response={}", response);
        return response;
    }

}
