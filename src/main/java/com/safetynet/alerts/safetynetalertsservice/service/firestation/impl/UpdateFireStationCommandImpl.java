package com.safetynet.alerts.safetynetalertsservice.service.firestation.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
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

    /**
     * Replaces {@code oldFireStation} with a new mapping pointing the same address
     * to {@code newStationNumber}.
     *
     * {@link LinkedHashSet} is used to preserve the insertion order of fire stations
     * in the persisted JSON. Throws if the old mapping is not found.
     *
     * @param oldFireStation   the existing mapping to replace (matched by address+station equality)
     * @param newStationNumber the replacement station number
     * @throws OldFireStationNotFoundException if {@code oldFireStation} does not exist
     */
    @Override
    public void execute(FireStation oldFireStation, Integer newStationNumber) {
        repo.update(oldData -> {
            // Keep order with LinkedHashSet
            Set<FireStation> fireStations = new LinkedHashSet<>(oldData.fireStations());

            if (fireStations.contains(oldFireStation)) {
                fireStations.remove(oldFireStation); // remove old mapping
                fireStations.add(new FireStation(oldFireStation.address(), (newStationNumber).toString()));
            } else {
                throw new OldFireStationNotFoundException(oldFireStation.address(), oldFireStation.station()
                );
            }
            return new DataWrapper(oldData.persons(), List.copyOf(fireStations), oldData.medicalRecords()
            );
        });
    }
}


