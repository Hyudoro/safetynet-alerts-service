package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateFireStationMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.TestableJsonDataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.impl.FireStationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;



import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class AddFireStationCommandImplIntegrationTest {

    @TempDir
    static Path tempDir; // temporary directory for the tests

    @Autowired
    FireStationServiceImpl service;

    @Autowired
    TestableJsonDataRepository jsonDataRepository;

    //avoid left over data between tests launches.
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        Path tempJson = tempDir.resolve("data.json");
        registry.add("data.file.path", tempJson::toString);
    }

    @BeforeEach
    void reset(){
        jsonDataRepository.resetToJsonSeed();
    }

    @Test
    void shouldAddTheFireStationToTheRepository() {
        service.addFireStation(new FireStation("Marting st","5"));
        assertTrue(
                jsonDataRepository.findAllFireStations().stream()
                        .anyMatch(fs -> fs.address().equals("Marting st") && fs.station().equals("5"))
        );
    }

    @Test
    void shouldThrowDuplicateFireStationException() {
        service.addFireStation(new FireStation("Marting st","5"));
        assertThrows(DuplicateFireStationMappingException.class, () -> service.addFireStation(new FireStation("Marting st","5")));
    }
}
