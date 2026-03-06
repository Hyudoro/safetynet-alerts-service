package com.safetynet.alerts.safetynetalertsservice.service.personinfo;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.personinfo.PersonInfoResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.personInfo.impl.PersonInfoServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.personInfo.interfaces.PersonInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PersonInfoServiceIntegrationTest {
    @Autowired DataRepository repository;
    private PersonInfoService service;
    @BeforeEach
    void setUp(){
        service = new PersonInfoServiceImpl(repository);
    }

    @Test
    void shouldGetPersonInfoWithRelativesIntegrationTest(){
        String lastName = "Boyd";
        PersonInfoResponseDTO response = service.getPersonInfo(lastName);
        int count = (int) repository.findAllPersons().stream().filter(p -> lastName.equals(p.lastName())).count();
        assertThat(response.personInfoDTOS().size()).isEqualTo(count);
    }

    @Test
    void shouldReturnEmptyListIfNoLastNameMatchingIntegrationTest(){
        assertThat(service.getPersonInfo("wronName").personInfoDTOS()).isEmpty();
    }






}
