package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.flood.Impl.FloodServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.flood.interfaces.FloodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flood")
public class FloodController {
    private final FloodService service;
    private final Logger logger = LogManager.getLogger(FloodController.class);

    public FloodController(FloodServiceImpl service){
        this.service = service;
    }

    @GetMapping("/stations")
    public FloodResponseDTO  getHouseHoldsUnderStations(@RequestParam List<String> stations){
        logger.info("Incoming request stations ={}",stations);
        return service.getHouseHoldsUnderStations(stations);
    }

}
