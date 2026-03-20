package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord.add.MedicalRecordAddDTO;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
    public void addMedicalRecord(@RequestBody @Valid MedicalRecordAddDTO request){
        logger.info("Adding Medical Record mapping lastName ={} firstName = {}", request.lastName(), request.firstName());
        MedicalRecord medicalRecord = new MedicalRecord(request.firstName(), request.lastName(), request.birthDate(),request.medications(),request.allergies());
        service.addMedicalRecord(medicalRecord);
        logger.info("Medical record added successfully for {} {}", request.firstName(), request.lastName());
    }

    @DeleteMapping("/{firstName}/{lastName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Validated
    public void deleteMedicalRecord(@PathVariable @NotBlank String firstName, @PathVariable @NotBlank String lastName){
        logger.info("Deleting Medical Record mapping lastName ={} firstName = {}", lastName, firstName );
        service.deleteMedicalRecord(new MedicalRecord.Id(firstName, lastName));
        logger.info("Medical record deleted successfully for {} {}", firstName, lastName);
    }


    @PutMapping("/{firstName}/{lastName}/allergies")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAllergiesMedicalRecord(@PathVariable @NotBlank String firstName, @PathVariable @NotBlank String lastName,
                                @RequestBody @Valid List<@NotBlank String> allergies)

    {
        logger.info("Updating medical record's allergies for {} {}", firstName, lastName);
        service.updateAllergyMedicalRecord(new MedicalRecord.Id(firstName, lastName), allergies);
        logger.info("Allergies updated successfully for {} {}", firstName, lastName);
    }

    @PutMapping("/{firstName}/{lastName}/medications")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedicationMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @RequestBody @Valid List<@NotBlank String> medication)

    {
        logger.info("Updating medical record's medication for {} {}", firstName, lastName);
        service.updateMedicationMedicalRecord(new MedicalRecord.Id(firstName, lastName), medication);
        logger.info("Medications updated successfully for {} {}", firstName, lastName);
    }










}
