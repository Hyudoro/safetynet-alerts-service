package com.safetynet.alerts.safetynetalertsservice.service.flood.Impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResidentDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.HouseholdDTO;
import com.safetynet.alerts.safetynetalertsservice.model.*;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.flood.interfaces.FloodService;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Aggregates household-level medical information for all addresses covered by one or more
 * fire stations, used by the emergency flood-alert endpoint.
 */
@Service
public class FloodServiceImpl implements FloodService {
    private static final Logger logger = LogManager.getLogger(FloodServiceImpl.class);
    private final DataRepository repository;

    public FloodServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns households grouped by address for all addresses covered by any of the given stations.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Collect the set of addresses mapped to any station in {@code stations}.</li>
     *   <li>Filter persons living at those addresses.</li>
     *   <li>Pre-build a {@code MedicalRecord.Id → MedicalHistory} map to avoid per-person lookups.</li>
     *   <li>Group the enriched residents by address into {@link HouseholdDTO} entries.</li>
     * </ol>
     *
     * @param stations list of station numbers to include; may contain multiple stations
     * @return a {@link FloodResponseDTO} with one {@link HouseholdDTO} per affected address
     */
    @Override
    public FloodResponseDTO getHouseHoldsUnderStations(List<String> stations) {
Set<String> addresses = repository.findAllFireStations().stream()
                .filter(fS -> stations.contains(fS.station()))
                .map(FireStation::address)
                .collect(Collectors.toSet());
        logger.debug("{} addresses resolved for stations={}: {}", addresses.size(), stations, addresses);

        List<Person> concernedPeople = repository.findAllPersons().stream().filter(
                p -> addresses.contains(p.address())
                ).toList();
        logger.debug("{} persons concerned by stations={}", concernedPeople.size(), stations);


        Map<MedicalRecord.Id, MedicalRecord.MedicalHistory> meds = repository.findAllMedicalRecords().stream().collect(Collectors.toMap(
                mR -> new MedicalRecord.Id(mR.firstName(), mR.lastName()),
                mR -> new MedicalRecord.MedicalHistory(mR.medications(), mR.allergies(), AgeCalculator.calculate(mR.birthDate())),
                (existing, replacement) -> existing));


        Map<String, List<FloodResidentDTO>> floodResidents =
                concernedPeople.stream().collect(Collectors.groupingBy(
                                Person::address,
                                Collectors.mapping(person -> {
                                    MedicalRecord.MedicalHistory pMeds = meds.get(new MedicalRecord.Id(person.firstName(), person.lastName()));
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
        logger.debug("{} households grouped for stations={}", households.size(), stations);

    return new  FloodResponseDTO(households);
    }

}
