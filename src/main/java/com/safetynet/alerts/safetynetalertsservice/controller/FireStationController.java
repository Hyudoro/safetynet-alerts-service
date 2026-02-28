package com.safetynet.alerts.safetynetalertsservice.controller;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.add.FireStationAddDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.delete.FireStationDeleteByAddressDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.delete.FireStationDeleteByStationDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.update.FireStationUpdateDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.FireStationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/firestation")
public class FireStationController {
    private final FireStationService service;

    private static final Logger logger = LogManager.getLogger(FireStationController.class);

    public FireStationController(FireStationService service) {
        this.service = service;
    }

    @GetMapping
    public FireStationResponseDTO getByStation(@RequestParam @NotBlank String stationNumber) {

        logger.info("Incoming request station={}", stationNumber);
        FireStationResponseDTO response = service.getResidentsByStation(stationNumber);
        logger.info("Response={}", response.residents().size());
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFireStation(@RequestBody @Valid FireStationAddDTO request) {
        logger.info("Adding fireStation mapping address={} station={}", request.address(), request.station());
        FireStation fireStation = new FireStation(request.address(), request.station());
        service.addFireStation(fireStation);
    }

    @PatchMapping //Because (address,station) is the identifier of FireStation.
    public void updateStation(@RequestBody @Valid FireStationUpdateDTO request) {
        logger.info("Updating fireStation mapping address={} oldStation={} newStation={}",
                request.address(), request.oldStationNumber(), request.newStationNumber());

        FireStation oldFireStation = new FireStation(request.address(), request.oldStationNumber());
        Integer stationNumber = Integer.valueOf(request.newStationNumber());
        service.updateFireStation(oldFireStation, stationNumber);
    }

    @DeleteMapping(params = "address")
    public void deleteFireStationsByAddress(@RequestParam @NotBlank String address){
        logger.info("Deleting FireStation(s) covering address={}",address);
        service.deleteMappingsByAddress(address);
    }

    @DeleteMapping(params = "stationNumber")
    public void deleteFireStationByStation(@RequestParam @NotBlank String stationNumber){
        logger.info("Deleting FireStation(s) with number ={}",stationNumber);
        service.deleteMappingsByStation(stationNumber);
    }

    @DeleteMapping(params = {"address", "stationNumber"})
    public void deleteFireStation(@RequestParam @NotBlank String address, @RequestParam @NotBlank String stationNumber){
        logger.info("Deleting FireStation mapping address={} station={} ",address,stationNumber);
        FireStation fireStation = new FireStation(address, stationNumber);
        service.deleteMapping(fireStation);
    }



}
