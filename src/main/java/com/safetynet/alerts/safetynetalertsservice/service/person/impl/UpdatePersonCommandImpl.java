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
    public void execute(String lastName, String firstName, Person person) {
        repository.update(oldData -> {
            List<Person> currentData = new ArrayList<>(oldData.persons());
            int pos = -1;
            for (int iterator = 0; iterator < currentData.size(); iterator++) {
                Person p = currentData.get(iterator);
                if (p.firstName().equals(firstName) && p.lastName().equals(lastName)) {
                    pos = iterator;
                    break;
                }
            }
            if (pos == -1) {
                throw new OldPersonNotFoundException(lastName, firstName);
            }
            currentData.set(pos, person);
            return new DataWrapper(currentData, List.of(), List.of());
        });
    }
}