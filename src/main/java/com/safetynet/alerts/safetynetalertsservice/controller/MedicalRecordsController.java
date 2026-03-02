package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord.add.MedicalRecordAddDTO;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

    @DeleteMapping
    public void DeleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
        logger.info("Deleting Medical Record mapping lastName ={} firstName = {}", lastName, firstName );
        service.deleteMedicalRecord(lastName, firstName);
    }


    @PutMapping("/{firstName}/{lastName}/allergies")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAllergiesMedicalRecord(@PathVariable String firstName, @PathVariable String lastName,
                                @RequestBody @Valid List<String> allergies)

    {
        logger.info("Updating medical record's allergies for {} {}", firstName, lastName);
        service.updateAllergyMedicalRecord(firstName, lastName, allergies);
    }

    @PutMapping("/{firstName}/{lastName}/medication")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedicationMedicalRecord(@PathVariable String firstName, @PathVariable String lastName,
                                @RequestBody @Valid List<String> medication)

    {
        logger.info("Updating medical record's medication for {} {}", firstName, lastName);
        service.updateMedicationMedicalRecord(firstName, lastName, medication);
    }

}
