package com.safetynet.alerts.safetynetalertsservice.controller;

import com.safetynet.alerts.safetynetalertsservice.dto.requests.person.PersonAddDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.person.PersonUpdateDTO;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final Logger logger = LogManager.getLogger(PersonController.class);
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@RequestParam @NotBlank String lastName, @RequestParam @NotBlank String firstName){
        logger.info("Deleting Person mapping lastname = {} firstname = {}", lastName, firstName);
        service.deletePerson(new Person.FullName(lastName, firstName));
        logger.info("Person deleted successfully lastName={} firstName={}", lastName, firstName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody @Valid PersonAddDTO request){
        logger.info("Adding Person mapping request = {}", request);

        Person person = new Person(
                request.firstName(),
                request.lastName(),
                request.address(),
                request.city(),
                request.zip(),
                request.phone(),
                request.email()
        );
        service.addPerson(person);
        logger.info("Person added successfully lastName={} firstName={}", request.lastName(), request.firstName());
    }

    @PatchMapping("/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Validated
    public void updatePerson(@PathVariable @NotBlank String lastName, @PathVariable @NotBlank String firstName,
                             @RequestBody @Valid PersonUpdateDTO newData){
        logger.info("Updating Person mapping lastname = {} firstname = {}", lastName, firstName);
        Person person = new Person(
                firstName,
                lastName,
                newData.address(),
                newData.city(),
                newData.zip(),
                newData.phone(),
                newData.email()
        );
        service.updatePerson(new Person.FullName(lastName, firstName), person);
        logger.info("Person updated successfully lastName={} firstName={}", lastName, firstName);
    }
}
