package com.safetynet.alerts.safetynetalertsservice.service.fire;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.FireResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.fire.interfaces.FireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class FireServiceImplIntegrationTest {

    @Autowired private FireService service;

    @Test
    void shouldReturnResidentsAndStationIntegrationTest(){
        FireResponseDTO response = service.getResidentMedicalByAddress("1509 Culver St");
        assertThat(response.residents()).hasSize(5);
        assertThat(response.stationNumber()).isEqualTo(List.of("3"));

    }

    @Test
    void shouldReturnEmptyResponseIntegrationTest(){
        FireResponseDTO response = service.getResidentMedicalByAddress("test");
        assertThat(response.stationNumber().isEmpty()).isTrue();
        assertThat(response.residents().isEmpty()).isTrue();

    }

}
