package com.safetynet.alerts.safetynetalertsservice.service.phonealert.impl;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PersonPhoneDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PhoneAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.phonealert.interfaces.PhoneAlertService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhoneAlertServiceImpl implements PhoneAlertService {
    private final DataRepository repository;

    public PhoneAlertServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns the phone numbers of every person living at an address covered by {@code stationNumber}.
     *
     * The station number is first resolved to its set of addresses; if no address is mapped
     * to that station a {@link MappingWithStationNotFoundException} is thrown rather than
     * returning an empty list, so callers can distinguish "station unknown" from "no residents".
     *
     * @param stationNumber the fire-station number to query
     * @return a response containing one {@link PersonPhoneDTO} per resident
     * @throws MappingWithStationNotFoundException if no address is mapped to {@code stationNumber}
     */
    @Override
    public PhoneAlertResponseDTO getPhonesByStation(String stationNumber) throws IllegalArgumentException {
        List<PersonPhoneDTO> personPhoneDTO = new ArrayList<>();
        Set<String> addresses = repository.findAllFireStations().stream()
                .filter(fs -> fs.station().equals(stationNumber))
                .map(FireStation::address)
                .collect(Collectors.toSet());

        if (addresses.isEmpty()) {
            throw new MappingWithStationNotFoundException(stationNumber);
        }

        for(Person person: repository.findAllPersons()){
            if(addresses.contains(person.address())){
                personPhoneDTO.add(new PersonPhoneDTO(
                        person.firstName(),
                        person.lastName(),
                        person.phone()
                ));
            }
        }
        return  new PhoneAlertResponseDTO(personPhoneDTO);
    }
}

