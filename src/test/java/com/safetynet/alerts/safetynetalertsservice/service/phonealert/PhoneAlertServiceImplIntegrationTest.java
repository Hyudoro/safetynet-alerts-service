package com.safetynet.alerts.safetynetalertsservice.service.phonealert;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PersonPhoneDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PhoneAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationNotFoundException;

import com.safetynet.alerts.safetynetalertsservice.service.phonealert.interfaces.PhoneAlertService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PhoneAlertServiceImplIntegrationTest {
    @Autowired private PhoneAlertService service;
    /*station 2
        29 15th St : Jonanathan Marrack 841-874-6513
        951 LoneTree Rd : Eric Cadigan 841-874-7458
        */
    @Test
    void shouldReturnPeoplePhoneNumberIntegrationTest() {
        PhoneAlertResponseDTO response = service.getPhonesByStation("2");
        List<String> phones = response.peoplePhoneNumber()
                .stream()
                .map(PersonPhoneDTO::phoneNumber)
                .toList();

        assertThat(phones).contains("841-874-6513", "841-874-7458");
    }
    @Test
    void shouldThrowsRunTimeExceptionIfAddressNotFoundIntegrationTest(){
        assertThatThrownBy(() -> service.getPhonesByStation("99")).isInstanceOf(MappingWithStationNotFoundException.class);
    }
}
