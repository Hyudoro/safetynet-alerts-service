package com.safetynet.alerts.safetynetalertsservice.service.person.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.Person;

public interface DeletePersonCommand {
    void execute(Person.FullName id);
}