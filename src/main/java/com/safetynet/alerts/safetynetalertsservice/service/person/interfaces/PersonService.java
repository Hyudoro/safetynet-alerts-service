package com.safetynet.alerts.safetynetalertsservice.service.person.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.Person;

public interface PersonService {
    void updatePerson(String lastName, String firstName, Person person);
    void deletePerson(String lastName, String firstName);
    void addPerson(Person person);
}
