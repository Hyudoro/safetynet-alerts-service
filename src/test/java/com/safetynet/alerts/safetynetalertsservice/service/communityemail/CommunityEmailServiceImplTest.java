package com.safetynet.alerts.safetynetalertsservice.service.communityemail;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.communityemail.CommunityEmailResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.communityEmail.impl.CommunityEmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceImplTest {
    @Mock DataRepository repository;
    private CommunityEmailServiceImpl service;
    @BeforeEach
    public void setUp() {
        service = new CommunityEmailServiceImpl(repository);
    }

    @Test
    void shouldReturnEmailsFromCityTest(){
        Person person1 = new Person("Emma", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        Person person2 = new Person("Claire", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-777-8888", "claire.durand@email.com");
        when(repository.findAllPersons()).thenReturn(List.of(person1,person2));
        CommunityEmailResponseDTO response = service.getCommunityEmail("Paris");
        assertThat(response.emails().size()).isEqualTo(2);
        assertThat(response.emails().contains(person1.email()));
        assertThat(response.emails().contains(person2.email()));
    }

    @Test
    void shouldReturnEmailsFromCityOnlyTest(){
        Person person1 = new Person("Emma", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        Person person2 = new Person("Claire", "Durand", "12 Rue des Fleurs", "Lyon", "06000", "060-777-8888", "claire.durand@email.com");
        when(repository.findAllPersons()).thenReturn(List.of(person1,person2));
        CommunityEmailResponseDTO response = service.getCommunityEmail("Paris");
        assertThat(response.emails().size()).isEqualTo(1);
        assertThat(response.emails().contains(person1.email()));
        assertThat(response.emails().contains(person2.email())).isFalse();
    }

    @Test
    void shouldReturnEmptyListIfCityNotFoundTest(){
        Person person1 = new Person("Emma", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        when(repository.findAllPersons()).thenReturn(List.of(person1));
        CommunityEmailResponseDTO response = service.getCommunityEmail("WrongCity");
        assertThat(response.emails().isEmpty()).isTrue();

    }





}
