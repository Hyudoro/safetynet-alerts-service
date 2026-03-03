package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.DeletePersonCommand;
import org.springframework.stereotype.Service;

@Service
public class DeletePersonCommandImpl implements DeletePersonCommand {
    private final DataRepository repository;

    public DeletePersonCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String lastName, String firstName) {

    }
}
