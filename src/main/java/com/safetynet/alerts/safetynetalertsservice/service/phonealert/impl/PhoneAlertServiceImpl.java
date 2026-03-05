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

