package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicatePersonMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.AddPersonCommand;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AddPersonCommandImpl implements AddPersonCommand {
    private final DataRepository repository;
    public AddPersonCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Person person) {
        repository.update(oldData -> {
            boolean alreadyExists = oldData.persons().stream().anyMatch(
                    pr -> pr.firstName().equals(person.firstName()) &&
                                  pr.lastName().equals(person.lastName()));
            if(alreadyExists){
                throw new DuplicatePersonMappingException(person.lastName(), person.firstName());
            }

            Set<Person> people = new HashSet<>(oldData.persons());
            people.add(person);
            return new DataWrapper(List.copyOf(people),oldData.fireStations(),oldData.medicalRecords());
        });
    }
}
