package com.safetynet.alerts.safetynetalertsservice.service.childalert;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildWithHouseHoldMembersDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildrenAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.HouseHoldMemberDTO;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.impl.ChildrenAlertServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.interfaces.ChildrenAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChildrenAlertServiceImplTest {
    @Mock DataRepository repository;
    ChildrenAlertService service;

    @BeforeEach
    public void setUp() {
        service = new ChildrenAlertServiceImpl(repository);
    }

    @Test
    void shouldReturnChildrenAndTheirHouseHoldMembersByAddress() {
        /*2 children, 3 adults, 1 child has 2 household members, and 1 only 1 household member

        Reminder : To be considered a child's household member, sharing the same (lastName, address) is required.

         */
        Person person1 = new Person("Emma", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        Person person2 = new Person("Claire", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-777-8888", "claire.durand@email.com");
        Person person3 = new Person("Julien", "Durand", "12 Rue des Fleurs", "Paris", "06000", "060-999-0000", "julien.durand@email.com");

        Person person4 = new Person("Lucas", "Martin", "12 Rue des Fleurs", "Paris", "06000", "060-333-4444", "lucas.martin@email.com");
        Person person5 = new Person("Sophie", "Martin", "12 Rue des Fleurs", "Paris", "06000", "060-121-2121", "sophie.martin@email.com");


        MedicalRecord mR1 = new MedicalRecord("Emma", "Durand", "04/12/2015", List.of(), List.of());
        MedicalRecord mR2 = new MedicalRecord("Lucas", "Martin", "09/03/2012", List.of(), List.of());
        MedicalRecord mR3 = new MedicalRecord("Claire", "Durand", "02/10/1983", List.of(), List.of());
        MedicalRecord mR4 = new MedicalRecord("Julien", "Durand", "11/25/1980", List.of(), List.of());
        MedicalRecord mR5 = new MedicalRecord("Sophie", "Martin", "07/14/1984", List.of(), List.of());

        when(repository.findAllPersons()).thenReturn(List.of(person1, person2, person3, person4, person5));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(mR1, mR2, mR3, mR4, mR5));

        ChildrenAlertResponseDTO response = service.getChildrenAndTheirHouseHoldMembersByAddress("12 Rue des Fleurs");



        List<ChildWithHouseHoldMembersDTO> childWithHouseHoldMembersDTOList = response.childAndHouseHoldMembers().stream().toList();
        ChildWithHouseHoldMembersDTO child1 = response.childAndHouseHoldMembers().get(0);
        ChildWithHouseHoldMembersDTO child2 = response.childAndHouseHoldMembers().get(1);
        List<HouseHoldMemberDTO> hMCh1 = child1.houseHoldMembers();
        List<HouseHoldMemberDTO> hMCh2 = child2.houseHoldMembers();

        assertThat(childWithHouseHoldMembersDTOList).hasSize(2);
        assertThat(hMCh1).hasSize(2);
        assertThat(hMCh2).hasSize(1);
    }

    @Test
    void shouldReturnEmptyResponseIfNoChildrenFoundAtThatAddress(){
        Person person1 = new Person("Emma", "Durand", "300 rue Moreno", "Paris", "06000", "060-111-2222", "emma.durand@email.com");
        MedicalRecord mR1 = new MedicalRecord("Emma", "Durand", "04/12/1900", List.of(), List.of());
        when(repository.findAllPersons()).thenReturn(List.of(person1));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(mR1));
        ChildrenAlertResponseDTO response = service.getChildrenAndTheirHouseHoldMembersByAddress("300 rue Moreno");
        assertThat(response).isNotNull();
        assertThat(response.childAndHouseHoldMembers()).isEmpty();
    }





}
