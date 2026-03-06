package com.safetynet.alerts.safetynetalertsservice.service.personinfo;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.personinfo.PersonInfoResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.personInfo.impl.PersonInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceImplTest {
    @Mock DataRepository repository;
    private PersonInfoServiceImpl service;
    @BeforeEach
    public void setUp() {
        service = new PersonInfoServiceImpl(repository);
    }
    @Test
    public void shouldGetPersonInfoWithRelativesTest() {
        Person person1 = new Person("Emma", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        Person person2 = new Person("Claire", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-777-8888", "claire.durand@email.com");
        MedicalRecord mR1 = new MedicalRecord("Emma", "Durand", "04/12/2015", List.of("paracetamol:350mg","insulin:200mg"),List.of("nillacilan","peanut","shellfish","sugar"));
        MedicalRecord mR2 = new MedicalRecord("Claire", "Durand", "02/10/1983", List.of("aznol:350mg","pharmacol:200mg"), List.of("nillacilan","peanut","shellfish"));
        when(repository.findAllPersons()).thenReturn(List.of(person1, person2));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(mR1, mR2));
        PersonInfoResponseDTO response= service.getPersonInfo("Durand");
        assertThat(response.personInfoDTOS().size()).isEqualTo(2);
        assertThat(response.personInfoDTOS().getFirst().age()).isEqualTo(10);
        assertThat(response.personInfoDTOS().getLast().medications().contains("aznol:350mg"));
    }

    @Test
    public void shouldReturnEmptyListIfNoLastNameMatchingTest() {
        when(repository.findAllPersons()).thenReturn(List.of());
        PersonInfoResponseDTO response= service.getPersonInfo("Claire");
        assertThat(response.personInfoDTOS().isEmpty()).isTrue();
    }




}
