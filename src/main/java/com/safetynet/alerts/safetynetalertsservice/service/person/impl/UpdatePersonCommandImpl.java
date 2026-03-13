package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.UpdatePersonCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class UpdatePersonCommandImpl implements UpdatePersonCommand {
    private final DataRepository repository;

    public UpdatePersonCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Person.FullName id, Person person) {
        repository.update(oldData -> {
            List<Person> currentData = new ArrayList<>(oldData.persons());
            int pos = -1;
            for (int iterator = 0; iterator < currentData.size(); iterator++) {
                Person p = currentData.get(iterator);
                if (p.firstName().equals(id.firstName()) && p.lastName().equals(id.lastName())) {
                    pos = iterator;
                    break;
                }
            }
            if (pos == -1) {
                throw new OldPersonNotFoundException(id.lastName(), id.firstName());
            }
            currentData.set(pos, person);
            return new DataWrapper(currentData, List.of(), List.of());
        });
    }
}