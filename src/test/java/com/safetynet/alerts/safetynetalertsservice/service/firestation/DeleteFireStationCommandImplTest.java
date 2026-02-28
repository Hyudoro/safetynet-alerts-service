package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationAndAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.impl.DeleteFireStationCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.impl.FireStationServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.AddFireStationCommand;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.ReadFireStationCommand;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.UpdateFireStationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteFireStationCommandImplTest {

    @Mock AddFireStationCommand addFireStationCommand;
    @Mock UpdateFireStationCommand updateFireStationCommand;
    @Mock ReadFireStationCommand readFireStationCommand;
    @Mock DataRepository repository;

    FireStationServiceImpl service;

    @BeforeEach
    void setUp(){
        DeleteFireStationCommandImpl deleteFireStationCommand = new DeleteFireStationCommandImpl(repository);
        service = new FireStationServiceImpl(readFireStationCommand,addFireStationCommand,updateFireStationCommand,deleteFireStationCommand);
    }
    // Deleting by address
    @Test
    void shouldDeleteMultipleFireStationsUsingMappingByAddressTest(){
            doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            FireStation fireStation1 = new FireStation("marvel st", "3");
            FireStation fireStation2 = new FireStation("marvel st", "5");

            List<FireStation> fireStations = new ArrayList<>();
            fireStations.add(fireStation1);
            fireStations.add(fireStation2);

            DataWrapper oldData = new DataWrapper(List.of(),List.copyOf(fireStations),List.of());
            DataWrapper newDataWrapper = lambda.apply(oldData);
            assertTrue(newDataWrapper.fireStations().isEmpty());
            return null;
        }).when(repository).update(any());
            service.deleteMappingsByAddress("marvel st");
    }

    @Test
    void shouldDeleteOneFireStationUsingMappingByAddressTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            FireStation fireStation1 = new FireStation("rick st", "3");
            FireStation fireStation2 = new FireStation("Morty st", "5");
            FireStation fireStation3 = new FireStation("Karmic st", "6");


            List<FireStation> fireStations = new ArrayList<>();
            fireStations.add(fireStation1);
            fireStations.add(fireStation2);
            fireStations.add(fireStation3);

            DataWrapper oldData = new DataWrapper(List.of(),List.copyOf(fireStations),List.of());
            DataWrapper newDataWrapper = lambda.apply(oldData);
            assertFalse(newDataWrapper.fireStations().contains(fireStation2));
            return null;
        }).when(repository).update(any());
        service.deleteMappingsByAddress("Morty st");
    }

    @Test
    void shouldThrowsRunTimeExceptionIfAddressNotFoundTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            DataWrapper oldData = new DataWrapper(List.of(),List.of(),List.of());
            return lambda.apply(oldData);
        }).when(repository).update(any());
        assertThrows(MappingWithAddressNotFoundException.class, () -> service.deleteMappingsByAddress("Morty st"));
    }


    // Deleting by address
    @Test
    void shouldDeleteMultipleFireStationsUsingMappingByStationTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            FireStation fireStation1 = new FireStation("marvel st", "3");
            FireStation fireStation2 = new FireStation("marvel st", "3");

            List<FireStation> fireStations = new ArrayList<>();
            fireStations.add(fireStation1);
            fireStations.add(fireStation2);

            DataWrapper oldData = new DataWrapper(List.of(),List.copyOf(fireStations),List.of());
            DataWrapper newDataWrapper = lambda.apply(oldData);
            assertTrue(newDataWrapper.fireStations().isEmpty());
            return null;
        }).when(repository).update(any());
        service.deleteMappingsByStation("3");
    }

    @Test
    void shouldDeleteOneFireStationUsingMappingByStationTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            FireStation fireStation1 = new FireStation("rick st", "3");
            FireStation fireStation2 = new FireStation("Morty st", "5");
            FireStation fireStation3 = new FireStation("Karmic st", "6");


            List<FireStation> fireStations = new ArrayList<>();
            fireStations.add(fireStation1);
            fireStations.add(fireStation2);
            fireStations.add(fireStation3);

            DataWrapper oldData = new DataWrapper(List.of(),List.copyOf(fireStations),List.of());
            DataWrapper newDataWrapper = lambda.apply(oldData);
            assertFalse(newDataWrapper.fireStations().contains(fireStation2));
            return null;
        }).when(repository).update(any());
        service.deleteMappingsByStation("5");
    }

    @Test
    void shouldThrowsRunTimeExceptionIfStationNotFoundTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            DataWrapper oldData = new DataWrapper(List.of(),List.of(),List.of());
            return lambda.apply(oldData);
        }).when(repository).update(any());
         assertThrows(MappingWithStationNotFoundException.class, () -> service.deleteMappingsByStation("5"));
    }

    // Deleting by (address,station)

    @Test
    void shouldDeleteTheCorrectFireStationsUsingAddressAndStationMappingTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            FireStation fireStation1 = new FireStation("rick st", "3");
            FireStation fireStation2 = new FireStation("Morty st", "5");
            FireStation fireStation3 = new FireStation("Karmic st", "6");

            List<FireStation> fireStations = new ArrayList<>();
            fireStations.add(fireStation1);
            fireStations.add(fireStation2);
            fireStations.add(fireStation3);

            DataWrapper oldData = new DataWrapper(List.of(),List.copyOf(fireStations),List.of());

            DataWrapper newDataWrapper = lambda.apply(oldData);

            assertFalse(newDataWrapper.fireStations().contains(fireStation1));
            assertEquals(2, newDataWrapper.fireStations().size());
            return null;
        }).when(repository).update(any());
        service.deleteMapping(new FireStation("rick st", "3"));
    }

    @Test
    void shouldThrowsRunTimeExceptionIfAddressAndStationIdentifierNotFoundTest(){
        doAnswer(invocation ->  {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            return lambda.apply(new DataWrapper(List.of(),List.of(),List.of()));
        }).when(repository).update(any());

        assertThrows(MappingWithStationAndAddressNotFoundException.class, () -> service.deleteMapping(new FireStation("rick st", "3")));
    }
}
