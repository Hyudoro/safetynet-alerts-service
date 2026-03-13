package com.safetynet.alerts.safetynetalertsservice.service.person.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.Person;

public interface PersonService {
    void updatePerson(Person.FullName id, Person person);
    void deletePerson(Person.FullName id);
    void addPerson(Person person);
}
