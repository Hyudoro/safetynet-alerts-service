package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class AddFireStationCommandImpl implements AddFireStationCommand {

    private final DataRepository repo;

    public AddFireStationCommandImpl(DataRepository repo) {
        this.repo = repo;
    }

    @Override
    public void execute(FireStation fs) {
        repo.update(oldData -> {

            List<FireStation> updatedStations =
                    new ArrayList<>(oldData.fireStations());

            updatedStations.add(fs);

            return new DataWrapper(
                    oldData.persons(),
                    List.copyOf(updatedStations),
                    oldData.medicalRecords()
            );
        });
    }
}

