package com.safetynet.alerts.safetynetalertsservice.service.firestation;

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
public class ReadFireStationCommandImpl implements ReadFireStationCommand{
    private final DataRepository repository;

    public ReadFireStationCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public FirestationResponseDTO getResidentsByStation(String stationNumber) {
        List<String> addresses = repository.findAllFireStations().stream()
                .filter(fs -> fs.station().equals(stationNumber))
                .map(FireStation::address)
                .toList();

        if (addresses.isEmpty()) {
            return new FirestationResponseDTO(List.of(), 0, 0);
        }

        List<Person> persons = repository.findAllPersons();
        List<MedicalRecord> records = repository.findAllMedicalRecords();

        List<ResidentDTO> residentDTOs = new ArrayList<>();
        long adultCount = 0;
        long childCount = 0;

        for (Person person : persons) {
            if (addresses.contains(person.address())) {

                MedicalRecord record = records.stream()
                        .filter(r -> r.firstName().equals(person.firstName()) //we check with name and firstname the medicalRecord of a person
                                && r.lastName().equals(person.lastName()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(
                                "Medical record not found for " + person.firstName() + " " + person.lastName()
                        ));

                int age = AgeCalculator.calculate(record.birthdate());

                if (age >= 18) adultCount++;
                else childCount++;

                residentDTOs.add(
                        new ResidentDTO(
                                person.firstName(),
                                person.lastName(),
                                person.address(),
                                person.phone()
                        )
                );
            }
        }

        return new FirestationResponseDTO(residentDTOs, adultCount, childCount);
    }


    //    public List<FireStation> findAllFireStations() {
//        return repository.findAllFireStations();
//    }
}
