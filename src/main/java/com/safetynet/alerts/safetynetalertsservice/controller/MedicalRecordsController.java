package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord.add.MedicalRecordAddDTO;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {
    private final MedicalRecordService service;
    private final Logger logger = LogManager.getLogger(MedicalRecordsController.class);


    public MedicalRecordsController(MedicalRecordService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void AddMedicalRecord(@RequestBody @Valid MedicalRecordAddDTO request){
        logger.info("Adding Medical Record mapping nom={} firstName = {}", request.firstName(), request.firstName());
        MedicalRecord medicalRecord = new MedicalRecord(request.firstName(), request.lastName(), request.birthDate(),request.medications(),request.allergies());
        service.addMedicalRecord(medicalRecord);
    }








}
