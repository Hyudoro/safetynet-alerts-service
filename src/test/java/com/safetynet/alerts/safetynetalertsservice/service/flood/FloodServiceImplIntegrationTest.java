package com.safetynet.alerts.safetynetalertsservice.service.flood;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;


import com.safetynet.alerts.safetynetalertsservice.service.flood.Impl.FloodServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.flood.interfaces.FloodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
public class FloodServiceImplIntegrationTest {
   FloodService service;
    @Autowired private DataRepository repository;

    @BeforeEach
    void setUp() {
        service = new FloodServiceImpl(repository);
    }
    @Test void shouldReturnHouseHoldsMembersUnderStationsIntegrationTest(){
        List<String>input = new ArrayList<>(List.of("3","4"));
        List<String> request = new ArrayList<>(input);
        FloodResponseDTO response = service.getHouseHoldsUnderStations(request);
        List<String> addressConcerned = repository.findAllFireStations().stream()
                .filter(fR -> input.contains(fR.station()))
                .map(FireStation::address).toList();

        assertThat(response.households().stream().allMatch(hs-> addressConcerned.contains(hs.address()))).isTrue();
    }
    @Test void shouldReturnEmptyListIntegrationTest(){
        assertThat(service.getHouseHoldsUnderStations(List.of()).households().isEmpty()).isTrue();

    }
}
