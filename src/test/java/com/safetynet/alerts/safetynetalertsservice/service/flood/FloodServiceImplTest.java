package com.safetynet.alerts.safetynetalertsservice.service.flood;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResidentDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.flood.FloodResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;

import com.safetynet.alerts.safetynetalertsservice.service.flood.Impl.FloodServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.flood.interfaces.FloodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FloodServiceImplTest {
    @Mock DataRepository repository;
    private FloodService service;
    @BeforeEach
    public void setUp() {
        service = new FloodServiceImpl(repository);
    }
    @Test
    void shouldReturnHouseHoldsMembersUnderStationsTest(){
        Person person1 = new Person("Emma", "Durand", "3 rue barbara", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        Person person2 = new Person("Claire", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-777-8888", "claire.durand@email.com");
        MedicalRecord mR1 = new MedicalRecord("Emma", "Durand", "04/12/2015", List.of("aznol:350mg","pharmacol:200mg"),List.of("nillacilan","peanut","shellfish"));
        MedicalRecord mR3 = new MedicalRecord("Claire", "Durand", "02/10/1983", List.of("nillacilan","peanut","shellfish"), List.of("nillacilan","peanut","shellfish"));
        FireStation fS1 = new FireStation("3 rue barbara", "5");
        FireStation fS2 = new FireStation("12 Rue des Fleurs", "6");
        when(repository.findAllFireStations()).thenReturn(List.of(fS1,fS2));
        when(repository.findAllPersons()).thenReturn(List.of(person1,person2));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(mR1,mR3));
        FloodResponseDTO response= service.getHouseHoldsUnderStations(List.of("6","5"));
        FloodResidentDTO fRD = response.households().getFirst().residents().getFirst();
        assertThat(response.households().size()).isEqualTo(2);
        assertThat(response.households().getFirst().address()).isEqualTo("12 Rue des Fleurs");
        assertThat(fRD.firstName()).isEqualTo("Claire");
    }
    @Test
    void shouldReturnEmptyList(){
        when(repository.findAllFireStations()).thenReturn(List.of());
        when(repository.findAllPersons()).thenReturn(List.of());
        when(repository.findAllMedicalRecords()).thenReturn(List.of());
        FloodResponseDTO response = service.getHouseHoldsUnderStations(List.of("wrongStations"));
        assertThat(response.households().isEmpty()).isTrue();
    }

}
