package com.safetynet.alerts.safetynetalertsservice.controller;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildrenAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.impl.ChildrenAlertServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.interfaces.ChildrenAlertService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
  Domain assumption here :
  A child is considered a child if he is under 18.
  A child's household members are defined as people sharing the same address and last name.
  This is a simplified rule and does not reflect all real-world household structures.
 */

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {
    private final Logger logger = LogManager.getLogger(ChildAlertController.class);
    private final ChildrenAlertService service;

    public ChildAlertController(ChildrenAlertServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{address}")
    public ChildrenAlertResponseDTO getChildrenByAddress(@RequestParam @NotBlank String address){
        logger.info("Getting children by address = {} ", address);
        return service.getChildrenAndTheirHouseHoldMembersByAddress(address);
    }


}
