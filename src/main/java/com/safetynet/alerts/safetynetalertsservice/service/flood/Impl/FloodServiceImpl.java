package com.safetynet.alerts.safetynetalertsservice.service.flood.Impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResidentDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.HouseholdDTO;
import com.safetynet.alerts.safetynetalertsservice.model.*;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.flood.interfaces.FloodService;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class FloodServiceImpl implements FloodService {
    private final DataRepository repository;

    public FloodServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public FloodResponseDTO getHouseHoldsUnderStations(List<String> stations) {
Set<String> addresses = repository.findAllFireStations().stream()
                .filter(fS -> stations.contains(fS.station()))
                .map(FireStation::address)
                .collect(Collectors.toSet());


        List<Person> concernedPeople = repository.findAllPersons().stream().filter(
                p -> addresses.contains(p.address())
                ).toList();


        Map<Person.FullName, MedicalRecord.MedicalHistory> meds = repository.findAllMedicalRecords().stream().collect(Collectors.toMap(
                mR -> new Person.FullName(mR.lastName(),mR.firstName()),
                mR -> new MedicalRecord.MedicalHistory(mR.medications(), mR.allergies(), AgeCalculator.calculate(mR.birthDate())),
                (existing, replacement) -> existing));


        Map<String, List<FloodResidentDTO>> floodResidents =
                concernedPeople.stream().collect(Collectors.groupingBy(
                                Person::address,
                                Collectors.mapping(person -> {
                                    MedicalRecord.MedicalHistory pMeds = meds.get(new Person.FullName(person.lastName(), person.firstName()));
                                    return new FloodResidentDTO(
                                            person.firstName(),
                                            person.lastName(),
                                            person.phone(),
                                            pMeds.age(),
                                            pMeds.medications(),
                                            pMeds.allergies()
                                    );
                                }, Collectors.toList())
                        ));
        List<HouseholdDTO> households = floodResidents.entrySet().stream().
                map(entry->new HouseholdDTO(entry.getKey(),entry.getValue())).toList();

    return new  FloodResponseDTO(households);
    }

}
