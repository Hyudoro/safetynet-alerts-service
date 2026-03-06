package com.safetynet.alerts.safetynetalertsservice.service.person;

import com.safetynet.alerts.safetynetalertsservice.model.Person;
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

@SpringBootTest
@ActiveProfiles("test")
public class UpdatePersonCommandImplIntegrationTest {
    @TempDir static Path tempDir;
    @Autowired private PersonService service;
    @Autowired TestableJsonDataRepository repository;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        Path tempJson = tempDir.resolve("data.json");
        registry.add("data.file.path", tempJson::toString);
    }

    @BeforeEach
    void setUp(){
        repository.resetToJsonSeed();
    }

    @Test
    void shouldUpdatePersonIntegrationTest(){
        service.updatePerson("Boyd","John", new Person(
                "John",
                "Boyd",
                "300 Busch Avenue",
                "Denver",
                "84024",
                "724-102-03042",
                "jonny.doe@email.com"
        ));
        Person updatedPerson = repository.findAllPersons().stream()
                .filter(p -> p.firstName().equals("John")
                        && p.lastName().equals("Boyd"))
                .findFirst()
                .orElseThrow();

        assertThat(updatedPerson)
                .usingRecursiveComparison()
                .isEqualTo(new Person(
                        "John",
                        "Boyd",
                        "300 Busch Avenue",
                        "Denver",
                        "84024",
                        "724-102-03042",
                        "jonny.doe@email.com"
                ));
    }

    @Test
    void shouldThrowsExceptionIfPersonNotFoundIntegrationTest(){
        assertThatThrownBy(() -> service.updatePerson("holliday","johnny", new Person(
                "John",
                "Doe",
                "300 Busch Avenue",
                "Denver",
                "84024",
                "724-102-03042",
                "jonny.doe@email.com"))).isInstanceOf(OldPersonNotFoundException.class);
    }

}
