package com.safetynet.alerts.safetynetalertsservice.service.communityEmail.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.communityemail.CommunityEmailResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.communityEmail.interfaces.CommunityEmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityEmailServiceImpl implements CommunityEmailService {
    private final DataRepository repository;


    public CommunityEmailServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public CommunityEmailResponseDTO getCommunityEmail(String city) {
        List<String> Emails = repository.findAllPersons().stream().
                filter(p-> city.equals(p.city())).
                map(Person::email).toList();
        return new CommunityEmailResponseDTO(Emails);
    }
}
