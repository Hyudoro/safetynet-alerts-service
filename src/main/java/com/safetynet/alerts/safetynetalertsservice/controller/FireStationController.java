package com.safetynet.alerts.safetynetalertsservice.controller;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.FireStationService;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/firestation")
public class FireStationController {
    private final FireStationService service;

    private static final Logger logger = LogManager.getLogger(FireStationController.class);

    public FireStationController(FireStationService service)
    {
        this.service = service;
    }
    @GetMapping
    public FireStationResponseDTO getByStation(@RequestParam @NotBlank String stationNumber) {

        logger.info("Incoming request station={}", stationNumber);
        FireStationResponseDTO response = service.getResidentsByStation(stationNumber);
        logger.info("Response={}", response.residents().size());
        return response;
    }


}
