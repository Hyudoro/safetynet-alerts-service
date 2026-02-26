package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateFireStationMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AddFireStationCommandImpl implements AddFireStationCommand {

    private final DataRepository repo;

    public AddFireStationCommandImpl(DataRepository repo) {
        this.repo = repo;
    }

    @Override
    public void execute(FireStation fs) {
        repo.update(oldData -> {
            boolean alreadyExists =
                    oldData.fireStations().stream().
                            anyMatch(
                                    existing -> existing.address().equals(fs.address())
                                    && existing.station().equals(fs.station()));
            // We could have made it Idempotent, but It would have violated the business logic rule.
            if (alreadyExists) {
                throw new DuplicateFireStationMappingException(fs.address(), fs.station());
            }

            Set<FireStation> updated = new HashSet<>(oldData.fireStations());
            updated.add(fs);

            return new DataWrapper(oldData.persons(), List.copyOf(updated), oldData.medicalRecords()
            );
        });
    }
}

