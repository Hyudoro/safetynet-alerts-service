package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.personinfo.PersonInfoResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.personInfo.interfaces.PersonInfoService;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {
    private final PersonInfoService service;
    Logger logger = LogManager.getLogger(PersonInfoController.class);
    public PersonInfoController(PersonInfoService service) {
        this.service = service;
    }
    @GetMapping
    public PersonInfoResponseDTO getPersonInfoDTOS(@RequestParam @NotBlank String lastName) {
        logger.info("Incoming request to get person info with lastName = {}", lastName); //hmm
        return service.getPersonInfo(lastName);
    }

}
