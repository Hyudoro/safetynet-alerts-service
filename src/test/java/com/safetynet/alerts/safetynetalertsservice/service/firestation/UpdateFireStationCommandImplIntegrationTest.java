package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldFireStationNotFoundException;
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
class UpdateFireStationCommandImplIntegrationTest {

    @TempDir static Path tempDir;

    @Autowired FireStationServiceImpl service;

    @Autowired TestableJsonDataRepository jsonDataRepository;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        Path tempJson =  tempDir.resolve("data.json");
        registry.add("data.file.path",tempJson::toString);
    }

    @BeforeEach
    void reset(){
        jsonDataRepository.resetToJsonSeed();
    }

    @Test
    void shouldUpdateFireStationStationNumber(){
        service.updateFireStation(new FireStation("29 15th St", "2"),99);
        assertTrue(jsonDataRepository.findAllFireStations().stream().anyMatch(fs ->
                fs.address().equals("29 15th St") && fs.station().equals("99")));
    }

    //(address, station) is the identifier itself of FireStation, therefore,
    // veryfing if one only doesn't respect the condition is sufficient.
    @Test
    void shouldThrowsRunTimeException(){
        assertThrows(OldFireStationNotFoundException.class,  () -> service.updateFireStation(new FireStation("29 15th st", "999"),99));
    }

}
