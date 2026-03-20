package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.UpdatePersonCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class UpdatePersonCommandImpl implements UpdatePersonCommand {
    private static final Logger logger = LogManager.getLogger(UpdatePersonCommandImpl.class);
    private final DataRepository repository;

    public UpdatePersonCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Replaces the person identified by {@code id} with {@code person} in-place,
     * preserving the list order via index-based {@code set()}.
     *
     * @param id the (firstName,lastName) key identifying the person to replace
     * @param person the new person data (may carry a different address, phone, etc.)
     * @throws OldPersonNotFoundException if no person with {@code id} exists
     */
    @Override
    public void execute(Person.FullName id, Person person) {
        logger.debug("Attempting to update person lastName={} firstName={}", id.lastName(), id.firstName());
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
                logger.error("Person not found for update: lastName={} firstName={}", id.lastName(), id.firstName());
                throw new OldPersonNotFoundException(id.lastName(), id.firstName());
            }
            currentData.set(pos, person);
            return new DataWrapper(currentData, List.of(), List.of());
        });
    }
}