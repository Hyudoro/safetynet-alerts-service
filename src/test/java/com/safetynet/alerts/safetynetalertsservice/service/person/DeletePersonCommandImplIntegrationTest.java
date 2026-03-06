package com.safetynet.alerts.safetynetalertsservice.service.person;

import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class DeletePersonCommandImplIntegrationTest {
        @TempDir static Path tempDir;
        @Autowired private PersonService service;
        @Autowired TestableJsonDataRepository repository;

        @DynamicPropertySource
        static void dynamicProperties(DynamicPropertyRegistry registry) {
            Path tempJson = tempDir.resolve("data.json");
            registry.add("data.file.path",tempJson::toString);
        }

        @BeforeEach
        void setUp() {
            repository.resetToJsonSeed();
        }

        @Test
        void shouldDeletePersonIntegrationTest() {
                service.deletePerson("Boyd","John");
                assertFalse(repository.findAllPersons().stream().anyMatch(
                        p -> p.lastName().equals("Boyd") && p.firstName().equals("John")));
        }

        @Test
        void shouldThrowsExceptionIfPersonDoesNotExist(){
            assertThatThrownBy(() -> service.deletePerson("Robert James","Fischer") ).isInstanceOf(OldPersonNotFoundException.class);
        }

}
