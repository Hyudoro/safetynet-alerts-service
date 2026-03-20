package com.safetynet.alerts.safetynetalertsservice.service.fire.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.FireResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.ResidentMedicalDTO;
import com.safetynet.alerts.safetynetalertsservice.model.*;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.fire.interfaces.FireService;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Returns the medical details of every resident at a given address together with
 * the fire-station number(s) covering that address.
 * Design note: the response uses {@code List<String>} for station numbers because
 * the data set shows that a single address can be covered by multiple stations,
 * even though the spec implies a one-to-one mapping. The worst case is a one-element list.
 */
@Service
public class FireServiceImpl implements FireService
{
    private static final Logger logger = LogManager.getLogger(FireServiceImpl.class);
    private final DataRepository repository;

    public FireServiceImpl(DataRepository repository) {
        this.repository = repository;
    }


    /**
     * Returns the medical summary of every resident at {@code address} plus the station
     * number(s) covering that address.
     *{@code MedicalRecord}.Id → MedicalHistory map is pre-built once from all medical
     * records to avoid a nested lookup per person. Persons without a matching medical record
     * are skipped.
     *
     * @param address the street address to query
     * @return a {@link FireResponseDTO} containing resident medical details and station numbers
     */
    @Override
    public FireResponseDTO getResidentMedicalByAddress(String address) {
        List<String> fireStationByAddress = repository.findAllFireStations().
                stream().filter(fS -> address.equals(fS.address()))
                .map(FireStation::station).toList();
        logger.debug("{} station(s) found for address={}: {}", fireStationByAddress.size(), address, fireStationByAddress);



        Map<MedicalRecord.Id, MedicalRecord.MedicalHistory> personMeds =
                repository.findAllMedicalRecords().stream()
                        .collect(Collectors.toMap(
                                key -> new MedicalRecord.Id(key.firstName(), key.lastName()),
                                value -> new MedicalRecord.MedicalHistory(value.medications(), value.allergies(), AgeCalculator.calculate(value.birthDate())),
                                (existing, replacement) -> existing));

        List<ResidentMedicalDTO> residentMedicalDTOS = new ArrayList<>();

        for(Person person: repository.findAllPersons()){
            if(person.address().equals(address)){
                MedicalRecord.MedicalHistory medicalHistory = personMeds.get(new MedicalRecord.Id(person.firstName(), person.lastName()));
                if(medicalHistory == null){
                    logger.debug("Skipping person {} {}: no medical record found", person.firstName(), person.lastName());
                    continue;
                }
                ResidentMedicalDTO newRMD = new ResidentMedicalDTO(
                        person.lastName(),
                        person.phone(),
                        medicalHistory.age(),
                        medicalHistory.medications(),
                        medicalHistory.allergies()
                );
                residentMedicalDTOS.add(newRMD);
            }
        }
        logger.debug("{} residents built for address={}", residentMedicalDTOS.size(), address);
        return new  FireResponseDTO(residentMedicalDTOS,fireStationByAddress);
    }
}
