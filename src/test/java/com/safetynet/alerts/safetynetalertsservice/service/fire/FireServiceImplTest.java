package com.safetynet.alerts.safetynetalertsservice.service.fire;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.FireResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.fire.impl.FireServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.fire.interfaces.FireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireServiceImplTest {
    @Mock DataRepository repository;
    FireService service ;

    @BeforeEach
    void setUp(){
        service = new FireServiceImpl(repository);
    }


    @Test
    void shouldReturnResidentsAndStationTest(){
        Person person1 = new Person("Emma", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        Person person2 = new Person("Claire", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-777-8888", "claire.durand@email.com");
        MedicalRecord mR1 = new MedicalRecord("Emma", "Durand", "04/12/2015",List.of("aznol:350mg","pharmacol:200mg"),List.of("nillacilan","peanut","shellfish"));
        MedicalRecord mR3 = new MedicalRecord("Claire", "Durand", "02/10/1983", List.of("nillacilan","peanut","shellfish"), List.of("nillacilan","peanut","shellfish"));
        FireStation fS1 = new FireStation("12 Rue des Fleurs", "5");

        when(repository.findAllPersons()).thenReturn(List.of(person1,person2));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(mR1,mR3));
        when(repository.findAllFireStations()).thenReturn(List.of(fS1));

        FireResponseDTO response =  service.getResidentMedicalByAddress("12 Rue des Fleurs");

        assertThat(response.stationNumber()).isEqualTo(List.of("5"));
        assertThat(response.residents().size()).isEqualTo(2);
    }

    @Test
    void shouldReturnEmptyResponseTest(){
        Person person1 = new Person("Emma", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        Person person2 = new Person("Claire", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-777-8888", "claire.durand@email.com");
        MedicalRecord mR1 = new MedicalRecord("Emma", "Durand", "04/12/2015",List.of("aznol:350mg","pharmacol:200mg"),List.of("nillacilan","peanut","shellfish"));
        MedicalRecord mR3 = new MedicalRecord("Claire", "Durand", "02/10/1983", List.of("nillacilan","peanut","shellfish"), List.of("nillacilan","peanut","shellfish"));
        FireStation fS1 = new FireStation("12 Rue des Fleurs", "5");

        when(repository.findAllPersons()).thenReturn(List.of(person1,person2));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(mR1,mR3));
        when(repository.findAllFireStations()).thenReturn(List.of(fS1));

        FireResponseDTO response =  service.getResidentMedicalByAddress("test");
        assertThat(response.stationNumber().isEmpty());
        assertThat(response.residents().isEmpty());
    }





}
