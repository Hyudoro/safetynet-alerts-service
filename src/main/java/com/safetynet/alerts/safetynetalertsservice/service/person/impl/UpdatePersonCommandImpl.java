package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.UpdatePersonCommand;
import org.springframework.stereotype.Service;

@Service
public class UpdatePersonCommandImpl implements UpdatePersonCommand {
    private final DataRepository repository;
    public UpdatePersonCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String lastName, String firstName, Person person) {
    }
}
