package com.safetynet.alerts.safetynetalertsservice.service.person.interfaces;

public interface DeletePersonCommand {
    void execute(String lastName, String firstName);
}
