package com.safetynet.alerts.safetynetalertsservice.service;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FirestationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.ResidentDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService implements FireStationServicePort{
    private final DataRepository repositoy;

    public FireStationService(DataRepository repositoy) {
        this.repositoy = repositoy;
    }


    @Override
    public FirestationResponseDTO getResidentsByStation(String stationNumber) {

        List<String> addresses = repositoy.findAllFireStations().stream()
                .filter(fs -> fs.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .toList();

        if (addresses.isEmpty()) {
            return new FirestationResponseDTO(List.of(), 0, 0);
        }

        List<Person> persons = repositoy.findAllPersons();
        List<MedicalRecord> records = repositoy.findAllMedicalRecords();

        List<ResidentDTO> residentDTOs = new ArrayList<>();
        long adultCount = 0;
        long childCount = 0;

        for (Person person : persons) {
            if (addresses.contains(person.getAddress())) {

                MedicalRecord record = records.stream()
                        .filter(r -> r.getFirstName().equals(person.getFirstName())
                                && r.getLastName().equals(person.getLastName()))
                        .findFirst()
                        .orElse(null);

                int age = AgeCalculator.calculate(record.getBirthdate());

                if (age >= 18) adultCount++;
                else childCount++;

                residentDTOs.add(
                        new ResidentDTO(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhone()
                        )
                );
            }
        }

        return new FirestationResponseDTO(residentDTOs, adultCount, childCount);
    }
}
