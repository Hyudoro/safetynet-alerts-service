package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldFireStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.impl.FireStationServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.impl.UpdateFireStationCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.AddFireStationCommand;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.DeleteFireStationCommand;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.ReadFireStationCommand;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateFireStationCommandImplTest {

    @Mock AddFireStationCommand addFireStationCommand;
    @Mock DeleteFireStationCommand deleteFireStationCommand;
    @Mock ReadFireStationCommand readFireStationCommand;
    @Mock DataRepository dataRepository;

    FireStationServiceImpl service;

    @BeforeEach
    void setUp() {
        UpdateFireStationCommandImpl updateFireStationCommand = new UpdateFireStationCommandImpl(dataRepository);
        service = new FireStationServiceImpl(readFireStationCommand, addFireStationCommand, updateFireStationCommand, deleteFireStationCommand);
    }

    @Test
    void shouldUpdateAFireStationStationNumberTest(){
        doAnswer(invocationOnMock -> {
            UnaryOperator<DataWrapper> lambda = invocationOnMock.getArgument(0);
            FireStation fS = new FireStation("The Krusty Krab", "5");
            List<FireStation> oldFireStations = new ArrayList<>();
            oldFireStations.add(fS);

            DataWrapper oldData = new DataWrapper(List.of(),List.copyOf(oldFireStations),List.of());

            DataWrapper newDataWrapper = lambda.apply(oldData);
            String stationNumberUpdated = newDataWrapper.fireStations().getFirst().station();
            assertEquals("3",stationNumberUpdated);
            return null;

        }).when(dataRepository).update(any());
        service.updateFireStation(new FireStation("The Krusty Krab", "5"),3);
        verify(dataRepository, times(1)).update(any());
    }

    @Test
    void shouldThrowsRunTimeExceptionIfOldFireStationNotFoundTest(){
        doAnswer(invocationOnMock -> {
            UnaryOperator<DataWrapper> lambda = invocationOnMock.getArgument(0);
            DataWrapper oldData = new DataWrapper(List.of(),List.of(),List.of());
            return lambda.apply(oldData);
        }).when(dataRepository).update(any());

        assertThrows(OldFireStationNotFoundException.class, () -> service.updateFireStation(new FireStation("The Krusty Krab", "5"),3));
        verify(dataRepository, times(1)).update(any());
    }
}
