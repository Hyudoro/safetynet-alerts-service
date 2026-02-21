package com.safetynet.alerts.safetynetalertsservice.service;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FirestationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

    @Mock
    private DataRepository repository;

    @InjectMocks
    private FireStationService service;

    @Test
    void shouldReturnResidentsAndCounts() {
        // Arrange
        FireStation firestation = new FireStation("1509 Culver St", "3");
        Person person = new Person("John", "Boyd",
                "1509 Culver St", "Culver", "97451",
                "841-874-6512", "john@email.com");
        MedicalRecord record = new MedicalRecord("John", "Boyd",
                "03/06/1984", new String[]{}, new String[]{});

        when(repository.findAllFirestations()).thenReturn(List.of(firestation));
        when(repository.findAllPersons()).thenReturn(List.of(person));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(record));

        // Act
        FirestationResponseDTO response = service.getResidentsByStation("3");

        // Assert
        assertEquals(1, response.residents().size(), "Expected 1 resident");
        assertEquals(1, response.adultCount(), "Expected 1 adult");
        assertEquals(0, response.childCount(), "Expected 0 children");
        assertEquals("John", response.residents().get(0).firstName(), "Resident name mismatch");

        // Verify interactions
        verify(repository).findAllFirestations();
        verify(repository).findAllPersons();
        verify(repository).findAllMedicalRecords();
    }

    @Test
    void shouldReturnEmptyResponseWhenStationNotFound() {
        // Arrange
        when(repository.findAllFirestations()).thenReturn(List.of());

        // Act
        FirestationResponseDTO response = service.getResidentsByStation("6");

        // Assert
        assertEquals(0, response.residents().size(), "Expected no residents");
        assertEquals(0, response.adultCount(), "Expected 0 adults");
        assertEquals(0, response.childCount(), "Expected 0 children");

        // Verify interactions
        verify(repository).findAllFirestations();
        verify(repository, never()).findAllPersons(); // shouldn't happen cause adress is empty
        verify(repository, never()).findAllMedicalRecords(); //

    }

    @Test
    void shouldCountChildrenCorrectly() {
        // Arrange
        FireStation firestation = new FireStation("1509 Culver St", "3");
        Person child = new Person("Roger", "Boyd",
                "1509 Culver St", "Culver", "97451",
                "841-874-6512", "roger@email.com");
        MedicalRecord childRecord = new MedicalRecord("Roger", "Boyd",
                "09/06/2017", new String[]{}, new String[]{});

        when(repository.findAllFirestations()).thenReturn(List.of(firestation));
        when(repository.findAllPersons()).thenReturn(List.of(child));
        when(repository.findAllMedicalRecords()).thenReturn(List.of(childRecord));

        // Act
        FirestationResponseDTO response = service.getResidentsByStation("3");

        // Assert
        assertEquals(0, response.adultCount(), "Expected 0 adults");
        assertEquals(1, response.childCount(), "Expected 1 child");
        assertEquals("Roger", response.residents().get(0).firstName(), "Resident name mismatch");

        // Verify interactions
        verify(repository).findAllFirestations();
        verify(repository).findAllPersons();
        verify(repository).findAllMedicalRecords();
    }
}