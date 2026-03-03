package com.safetynet.alerts.safetynetalertsservice.service.person.impl;

import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.AddPersonCommand;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.DeletePersonCommand;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.UpdatePersonCommand;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private final AddPersonCommand  addPersonCommand;
    private final DeletePersonCommand  deletePersonCommand;
    private final UpdatePersonCommand  updatePersonCommand;

    public PersonServiceImpl(AddPersonCommand addPersonCommand, DeletePersonCommand deletePersonCommand, UpdatePersonCommand updatePersonCommand) {
        this.addPersonCommand = addPersonCommand;
        this.deletePersonCommand = deletePersonCommand;
        this.updatePersonCommand = updatePersonCommand;
    }


    @Override
    public void updatePerson(String lastName, String firstName, Person person) {
        updatePersonCommand.execute(lastName, firstName, person);
    }

    @Override
    public void deletePerson(String lastName, String firstName) {
        deletePersonCommand.execute(lastName, firstName);
    }

    @Override
    public void addPerson(Person person) {
        addPersonCommand.execute(person);
    }
}
