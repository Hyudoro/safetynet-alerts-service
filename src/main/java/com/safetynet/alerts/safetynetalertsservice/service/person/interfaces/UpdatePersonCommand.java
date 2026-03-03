package com.safetynet.alerts.safetynetalertsservice.service.person.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.Person;

public interface UpdatePersonCommand {
    //we request 2 Pathvariables (identifier(firstName,lastName)), the body contains the dto shuch as:
    // (address, city, czip, phone, email)
    void execute(String lastName, String firstName,Person person);
}
