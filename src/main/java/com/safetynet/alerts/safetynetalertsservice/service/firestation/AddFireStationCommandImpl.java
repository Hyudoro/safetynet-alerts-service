package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddFireStationCommandImpl implements AddFireStationCommand {

    private final DataRepository repo;

    public AddFireStationCommandImpl(DataRepository repo) {
        this.repo = repo;
    }

    @Override
    public void execute(FireStation fs) {
        repo.update(oldData -> {boolean alreadyExists = oldData.fireStations().stream()
                    .anyMatch(existing -> existing.address().equals(fs.address()) && existing.station().equals(fs.station()));

            if (alreadyExists) {
                throw new IllegalArgumentException("Fire station mapping already exists for address " + fs.address() + " and station " + fs.station());
            }

            List<FireStation> updatedStations = new ArrayList<>(oldData.fireStations());

            updatedStations.add(fs);

            return new DataWrapper(oldData.persons(), List.copyOf(updatedStations), oldData.medicalRecords()
            );
        });
    }
}

