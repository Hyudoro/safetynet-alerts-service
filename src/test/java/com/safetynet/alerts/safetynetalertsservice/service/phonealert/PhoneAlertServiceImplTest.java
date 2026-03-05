package com.safetynet.alerts.safetynetalertsservice.service.phonealert;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PersonPhoneDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PhoneAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.phonealert.impl.PhoneAlertServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.phonealert.interfaces.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceImplTest {
    @Mock DataRepository repository;
    PhoneAlertService service;

    @BeforeEach
    void setUp() {
        service = new PhoneAlertServiceImpl(repository);
    }

    @Test
    void shouldReturnPeoplePhoneNumberByAddressCoveredByStationTest(){
        Person person1 = new Person("Emma", "Durand", "130 baracouda St", "", "", "060-111-2222", "");
        Person person2 = new Person("Claire", "Durand", "130 baracouda St", "", "", "060-777-8888", "");
        FireStation fS1 = new FireStation("130 baracouda St", "5");

        when(repository.findAllPersons()).thenReturn(List.of(person1,person2));
        when(repository.findAllFireStations()).thenReturn(List.of(fS1));

        PhoneAlertResponseDTO response =  service.getPhonesByStation("5");
        List<PersonPhoneDTO> personPhoneDTOS = response.peoplePhoneNumber();
        PersonPhoneDTO personPhone1 = personPhoneDTOS.get(0);
        PersonPhoneDTO personPhone2 = personPhoneDTOS.get(1);
        assertThat(personPhoneDTOS.size()).isEqualTo(2);
        assertThat(personPhoneDTOS.stream().allMatch(p -> p.equals(personPhone1) || p.equals(personPhone2))).isTrue();
    }
    @Test
    void shouldThrowsRunTimeExceptionIfNoAddressFoundTest(){
        when(repository.findAllFireStations()).thenReturn(List.of());
        assertThatThrownBy(() -> service.getPhonesByStation("5")).isInstanceOf(MappingWithStationNotFoundException.class);
    }

    @Test
    void shouldReturnEmptyListIfNoPersonFoundTest(){
        FireStation fS1 = new FireStation("130 baracouda St", "5");
        when(repository.findAllFireStations()).thenReturn(List.of(fS1));
        when(repository.findAllPersons()).thenReturn(List.of());
        PhoneAlertResponseDTO response =  service.getPhonesByStation("5");
        assertThat(response.peoplePhoneNumber().isEmpty());
    }

}
