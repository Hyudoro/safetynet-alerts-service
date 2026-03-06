package com.safetynet.alerts.safetynetalertsservice.service.communityemail;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.communityemail.CommunityEmailResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.communityEmail.impl.CommunityEmailServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.communityEmail.interfaces.CommunityEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CommunityEmailServiceImplIntegrationTest {

    private CommunityEmailService service;
    @Autowired private DataRepository repository;

    @BeforeEach
    void setUp() {
        service = new CommunityEmailServiceImpl(repository);
    }

    @Test
    void shouldReturnEmailsFromCityIntegrationTest(){
        String city = "Culver";
        CommunityEmailResponseDTO response = service.getCommunityEmail(city);
        int counter = (int) repository.findAllPersons().stream().filter(
                p -> city.equals(p.city()) && !p.email().isEmpty()
        ).count();
        assertThat(counter).isEqualTo(response.emails().size());
    }
    @Test
    void shouldReturnEmptyListIfCityNotFoundIntegrationTest()
    {
        CommunityEmailResponseDTO response = service.getCommunityEmail("WrongCity");
        assertThat(response.emails()).isEmpty();
    }


}
