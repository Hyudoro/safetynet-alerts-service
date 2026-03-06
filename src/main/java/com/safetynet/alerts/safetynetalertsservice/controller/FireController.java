package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.FireResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.fire.interfaces.FireService;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fire")
public class FireController {
    private final FireService  service;
    private final Logger logger = LogManager.getLogger(FireController.class);

    public FireController(FireService service) {
        this.service = service;
    }

    @GetMapping
    public FireResponseDTO getResidentMedicalByAddress(@RequestParam @NotBlank String address) {
        logger.info("Incoming request address = {}", address);
        return service.getResidentMedicalByAddress(address);
    }
}
