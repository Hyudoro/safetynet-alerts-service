package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.DeletePersonCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DeletePersonCommandImpl implements DeletePersonCommand {
    private static final Logger logger = LogManager.getLogger(DeletePersonCommandImpl.class);
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
        logger.debug("Attempting to delete person lastName={} firstName={}", id.lastName(), id.firstName());
        repository.update(oldData -> {
            Set<Person> oldPeople = new HashSet<>(oldData.persons());
            boolean removed = oldPeople.removeIf(person ->
                                                         person.lastName().equals(id.lastName()) &&
                                                         person.firstName().equals(id.firstName()));
            if (!removed) {
                logger.error("Person not found: lastName={} firstName={}", id.lastName(), id.firstName());
                throw new OldPersonNotFoundException(id.lastName(), id.firstName());
            }
            return new DataWrapper(List.copyOf(oldPeople),oldData.fireStations(),oldData.medicalRecords());
        });
    }
}
