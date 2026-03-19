package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
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

    /**
     * Removes the person identified by {@code id} (firstName,lastName) from the repository.
     *
     * @param id the firstName+lastName key identifying the person to delete
     * @throws OldPersonNotFoundException if no person with {@code id} exists
     */
    @Override
    public void execute(Person.FullName id) {
        repository.update(oldData -> {
            Set<Person> oldPeople = new HashSet<>(oldData.persons());
            boolean removed = oldPeople.removeIf(person ->
                                                         person.lastName().equals(id.lastName()) &&
                                                         person.firstName().equals(id.firstName()));
            if (!removed) {
                throw new OldPersonNotFoundException(id.lastName(), id.firstName());
            }
            return new DataWrapper(List.copyOf(oldPeople),oldData.fireStations(),oldData.medicalRecords());
        });
    }
}
