package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicatePersonMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.AddPersonCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AddPersonCommandImpl implements AddPersonCommand {
    private static final Logger logger = LogManager.getLogger(AddPersonCommandImpl.class);
    private final DataRepository repository;
    public AddPersonCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new person to the repository.
     *
     * Identity is determined by the (firstName,lastName pair). Adding a person whose
     * full name already exists throws {@link DuplicatePersonMappingException}.
     *
     * @param person the person to add
     * @throws DuplicatePersonMappingException if a person with the same (firstName,lastName) already exists
     */
    @Override
    public void execute(Person person) {
        logger.debug("Attempting to add person lastName={} firstName={}", person.lastName(), person.firstName());
        repository.update(oldData -> {
            boolean alreadyExists = oldData.persons().stream().anyMatch(
                    pr -> pr.firstName().equals(person.firstName()) &&
                                  pr.lastName().equals(person.lastName()));
            if(alreadyExists){
                logger.error("Duplicate person: lastName={} firstName={} already exists", person.lastName(), person.firstName());
                throw new DuplicatePersonMappingException(person.lastName(), person.firstName());
            }

            Set<Person> people = new HashSet<>(oldData.persons());
            people.add(person);
            return new DataWrapper(List.copyOf(people),oldData.fireStations(),oldData.medicalRecords());
        });
    }
}
