package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationAndAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationNotFoundException;
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



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteFireStationCommandImplIntegrationTest {

    @TempDir
    static Path temp;

    @Autowired FireStationServiceImpl service;

    @Autowired TestableJsonDataRepository repository;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        Path tempJson = temp.resolve("data.json");
        registry.add("data.file.path",tempJson::toString);
    }

    @BeforeEach
    void reset(){
        repository.resetToJsonSeed();
    }
    //Delete with address mapping
    @Test
    void shouldDeleteMultipleFireStationsUsingMappingByAddressIntegrationTest(){
        service.deleteMappingsByAddress("112 Steppes Pl");
        assertFalse(repository.findAllFireStations().stream().anyMatch(
                fs -> fs.address().equals("112 Steppes Pl")));
    }
    @Test
    void shouldDeleteOneFireStationsUsingMappingByAddressIntegrationTest(){
        int fireStationsLength = repository.findAllFireStations().size();
        service.deleteMappingsByAddress("1509 Culver St");
        assertEquals(fireStationsLength -1 ,repository.findAllFireStations().size());
    }

    @Test
    void shouldThrowsRunTimeExceptionIfAddressNotFoundTest() {
        assertThrows(MappingWithAddressNotFoundException.class, () -> service.deleteMappingsByAddress("scp 200"));
    }

    //Delete with station mapping
    @Test
    void shouldDeleteMultipleFireStationsUsingMappingByStationIntegrationTest(){
        service.deleteMappingsByAddress("112 Steppes Pl");
        assertFalse(repository.findAllFireStations().stream().anyMatch(
                fs -> fs.address().equals("112 Steppes Pl")));
    }
    @Test
    void shouldDeleteOneFireStationsUsingMappingByStationIntegrationTest(){
        int fireStationsLength = repository.findAllFireStations().size();
        service.deleteMappingsByAddress("1509 Culver St");
        assertEquals(fireStationsLength -1 ,repository.findAllFireStations().size());
    }

    @Test
    void shouldThrowsRunTimeExceptionIfStationNotFoundTest() {
        assertThrows(MappingWithStationNotFoundException.class, () -> service.deleteMappingsByStation("200"));
    }

    //Delete with (station, address) mapping
    @Test
    void shouldDeleteFireStationsUsingAddressAndStationMappingIntegrationTest(){
        FireStation fs = new FireStation("947 E. Rose Dr","1");
        service.deleteMapping(fs);
        assertFalse(repository.findAllFireStations().contains(fs));
    }

    @Test
    void shouldThrowsRunTimeExceptionIfAddressAndStationIdentifierNotFoundIntegrationTest(){
        assertThrows(MappingWithStationAndAddressNotFoundException.class, () -> service.deleteMapping(new FireStation("scp","99")));
    }




}
