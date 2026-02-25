package com.safetynet.alerts.safetynetalertsservice.service.firestation;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ReadFireStationCommandImplIntegrationTest {

    @Autowired
    private FireStationServiceImpl service;
    @Test
    void shouldReturnResidentsAndAdultsAndChildrenCountsIntegrationTest() {
        FireStationResponseDTO response = service.getResidentsByStation("4");
        assertEquals(4, response.residents().size(), "Expected 5 resident");
        assertEquals(4, response.adultCount(), "Expected 3 adults");
        assertEquals(0, response.childCount(), "Expected 2 children");
    }

    @Test
    void ShouldReturnEmptyListAndNullForCountsIfFireStationIsntFoundedIntegrationTest(){

        FireStationResponseDTO response = service.getResidentsByStation("99");
        assertEquals(0, response.adultCount(), "Expected 0 adults");
        assertEquals(0, response.childCount(), "Expected 0 children");
        assertTrue(response.residents().isEmpty(), "Expected 0 residents");
            }


}
