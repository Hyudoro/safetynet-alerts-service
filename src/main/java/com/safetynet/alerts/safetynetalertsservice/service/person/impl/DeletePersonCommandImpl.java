package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.DeletePersonCommand;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DeletePersonCommandImpl implements DeletePersonCommand {
    private final DataRepository repository;

    public DeletePersonCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String lastName, String firstName) {
        repository.update(oldData -> {
            Set<Person> oldPeople = new HashSet<>(oldData.persons());
            boolean removed = oldPeople.removeIf(person ->
                                                         person.lastName().equals(lastName) &&
                                                         person.firstName().equals(firstName));
            if (!removed) {
                throw new OldPersonNotFoundException(lastName, firstName);
            }
            return new DataWrapper(List.copyOf(oldPeople),oldData.fireStations(),oldData.medicalRecords());
        });
    }
}
