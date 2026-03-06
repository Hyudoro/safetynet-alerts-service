package com.safetynet.alerts.safetynetalertsservice.service.person;

import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicatePersonMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.TestableJsonDataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class AddPersonCommandImplIntegrationTest {
    @TempDir static Path tempDir;
    @Autowired TestableJsonDataRepository repository;
    @Autowired private PersonService service;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        Path tempJson = tempDir.resolve("data.json");
        registry.add("data.file.path",tempJson::toFile);
    }

    @BeforeEach
    void setUp(){
    repository.resetToJsonSeed();
    }

    @Test
    void shouldAddPersonCommandIntegrationTest(){
        service.addPerson(new Person(
                "John",
                "Doe",
                "123 Main Street",
                "Paris",
                "06000",
                "060-102-03042",
                "john.doe@email.com"

        ));
        assertTrue(repository.findAllPersons().stream().anyMatch(
                p -> p.lastName().equals("Doe") &&
                        p.firstName().equals("John")
        ));
    }

    @Test
    void shouldThrowsRunTimeExceptionIfPersonAlreadyExistsIntegrationTest(){
        assertThatThrownBy(() -> service.addPerson(new Person(
                "Lily",
                "Cooper",
                "489 Manchester St",
                "Culver",
                "97451",
                "841-874-9845",
                "lily@email.com"
        ))).isInstanceOf(DuplicatePersonMappingException.class);
    }

}
