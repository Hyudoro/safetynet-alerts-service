package com.safetynet.alerts.safetynetalertsservice.service.firestation;
import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateFireStationMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddFireStationCommandImplTest {
    @Mock ReadFireStationCommand readFireStationCommand;
    @Mock DeleteFireStationCommand deleteFireStationCommand;
    @Mock UpdateFireStationCommand updateFireStationCommand;
    @Mock DataRepository dataRepository;

    private FireStationServiceImpl service;

    @BeforeEach
    void setUp() {
        AddFireStationCommand addCommand = new AddFireStationCommandImpl(dataRepository);
            service = new FireStationServiceImpl(readFireStationCommand,addCommand,updateFireStationCommand,deleteFireStationCommand);
    }
    @Test
    void addFireStationShouldCallRepositoryWithNewStationTest() {
        FireStation fS = new FireStation("126 Soslita St", "8");

        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);

            // old data is empty
            DataWrapper oldData = new DataWrapper(List.of(), List.of(), List.of());

            // apply the lambda like the real command would
            DataWrapper newData = lambda.apply(oldData);

            // Assertions
            assertEquals(1, newData.fireStations().size());
            FireStation added = newData.fireStations().getFirst();
            assertEquals("126 Soslita St", added.address());
            assertEquals("8", added.station());

            return null;
        }).when(dataRepository).update(any());


        service.addFireStation(fS);

        // verify update() was called exactly once
        verify(dataRepository, times(1)).update(any());
    }

    @Test
    void addFireStationShouldThrowsRunTimeExceptionIfFireStationAlreadyExistsTest() {
        FireStation fS1 = new FireStation("130 baracouda St", "5");
        FireStation fS2 = new FireStation("130 baracouda St", "5");
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            DataWrapper oldData = new DataWrapper(List.of(), List.of(), List.of());
            List<FireStation> lFS = new ArrayList<>(oldData.fireStations());
            lFS.add(fS1);
            DataWrapper currentData = new DataWrapper(List.of(),List.copyOf(lFS), List.of());
            DataWrapper newData = lambda.apply(currentData);
            return newData;
        }).when(dataRepository).update(any());

        // The service should now throw when adding fS2 (duplicate)
        assertThrows(DuplicateFireStationMappingException.class, () -> service.addFireStation(fS2));

        // Verify update was called
        verify(dataRepository, times(1)).update(any());

    }
}


