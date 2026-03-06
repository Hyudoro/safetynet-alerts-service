package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.communityemail.CommunityEmailResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.communityEmail.impl.CommunityEmailServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.communityEmail.interfaces.CommunityEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {
    private final CommunityEmailService service;
    private final Logger logger = LogManager.getLogger(CommunityEmailController.class);
    public CommunityEmailController(CommunityEmailServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public CommunityEmailResponseDTO getCommunityEmail(@RequestParam String city) {
        logger.info("Incoming request city = {} ", city);
        return service.getCommunityEmail(city);
    }

}
