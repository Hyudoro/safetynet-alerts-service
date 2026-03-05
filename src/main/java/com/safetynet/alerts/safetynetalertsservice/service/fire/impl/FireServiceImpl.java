package com.safetynet.alerts.safetynetalertsservice.service.fire.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.FireResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.ResidentMedicalDTO;
import com.safetynet.alerts.safetynetalertsservice.model.*;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.fire.interfaces.FireService;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// As seen in the data set, an address can be covered my multiple stations,
// Even tho the specs insinuate quite the opposite,
// I prefer Using List<String>, Worst case scenario : one element per list.
@Service
public class FireServiceImpl implements FireService
{
    private final DataRepository repository;

    public FireServiceImpl(DataRepository repository) {
        this.repository = repository;
    }


    @Override
    public FireResponseDTO getResidentMedicalByAddress(String address) {
        List<String> fireStationByAddress = repository.findAllFireStations().
                stream().filter(fS -> address.equals(fS.address()))
                .map(FireStation::station).toList();



        Map<FullName, MedicalHistory> personMeds =
                repository.findAllMedicalRecords().stream()
                        .collect(Collectors.toMap(
                                key -> new FullName(key.lastName(), key.firstName()),
                                value -> new MedicalHistory(value.medications(), value.allergies(), AgeCalculator.calculate(value.birthDate())),
                                (existing, replacement) -> existing
                        ));

        List<ResidentMedicalDTO> residentMedicalDTOS = new ArrayList<>();

        for(Person person: repository.findAllPersons()){
            if(person.address().equals(address)){
                MedicalHistory medicalHistory = personMeds.get(new FullName(person.lastName(), person.firstName()));
                if(medicalHistory == null){ continue;}
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
        return new  FireResponseDTO(residentMedicalDTOS,fireStationByAddress);
    }
}
