package com.safetynet.alerts.safetynetalertsservice.service.firestation.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.ResidentDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.ReadFireStationCommand;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadFireStationCommandImpl implements ReadFireStationCommand {
    private static final Logger logger = LogManager.getLogger(ReadFireStationCommandImpl.class);
    private final DataRepository repository;

    public ReadFireStationCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns all persons living at addresses covered by {@code stationNumber} together with
     * an adult/child head-count derived from their medical records.
     *
     * If no address is mapped to the station an empty response (zero residents, zero counts)
     * is returned rather than an error. For each matched person the medical record is looked up
     * by firstName+lastName; an {@link IllegalStateException} is thrown if it is missing, as
     * that indicates corrupted data rather than a normal "not found" case.
     *
     * @param stationNumber the fire-station number to query
     * @return a {@link FireStationResponseDTO} with resident list and adult/child counts
     * @throws IllegalStateException if a covered person has no matching medical record
     */
    @Override
    public FireStationResponseDTO getResidentsByStation(String stationNumber) {
        List<String> addresses = repository.findAllFireStations().stream()
                .filter(fs -> fs.station().equals(stationNumber))
                .map(FireStation::address)
                .toList();
        logger.debug("{} addresses found for station={}", addresses.size(), stationNumber);

        if (addresses.isEmpty()) {
            logger.debug("No addresses mapped to station={}, returning empty response", stationNumber);
            return new FireStationResponseDTO(List.of(), 0, 0);
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
                        .orElseThrow(() -> {
                            logger.error("Data corruption: medical record not found for person {} {}", person.firstName(), person.lastName());
                            return new IllegalStateException(
                                    "Medical record not found for " + person.firstName() + " " + person.lastName()
                            );
                        });

                int age = AgeCalculator.calculate(record.birthDate());
                logger.debug("Person {} {}: age={}, category={}", person.firstName(), person.lastName(), age, age >= 18 ? "adult" : "child");

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

        logger.debug("Final counts for station={}: {} adults, {} children, {} total residents", stationNumber, adultCount, childCount, residentDTOs.size());
        return new FireStationResponseDTO(residentDTOs, adultCount, childCount);
    }
}
