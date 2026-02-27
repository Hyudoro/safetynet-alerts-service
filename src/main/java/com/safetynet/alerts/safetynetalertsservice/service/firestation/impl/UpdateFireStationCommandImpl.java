package com.safetynet.alerts.safetynetalertsservice.service.firestation.impl;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldFireStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.UpdateFireStationCommand;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UpdateFireStationCommandImpl implements UpdateFireStationCommand {
    private final DataRepository repo;

    public UpdateFireStationCommandImpl(DataRepository repo) {
        this.repo = repo;
    }

    @Override
    public void execute(FireStation oldFireStation, Integer newStationNumber) {
        repo.update(oldData -> {
            // Keep order with LinkedHashSet
            Set<FireStation> fireStations = new LinkedHashSet<>(oldData.fireStations());

            if (!fireStations.remove(oldFireStation)) {
                throw new OldFireStationNotFoundException(
                        oldFireStation.address(), oldFireStation.station()
                );
            }

            // Add updated mapping
            fireStations.add(new FireStation(oldFireStation.address(), (newStationNumber).toString()));

            return new DataWrapper(oldData.persons(), List.copyOf(fireStations), oldData.medicalRecords()
            );
        });
    }
}


