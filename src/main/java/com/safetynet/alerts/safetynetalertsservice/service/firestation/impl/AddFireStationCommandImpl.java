package com.safetynet.alerts.safetynetalertsservice.service.firestation.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateFireStationMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.AddFireStationCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AddFireStationCommandImpl implements AddFireStationCommand {
    private static final Logger logger = LogManager.getLogger(AddFireStationCommandImpl.class);

    private final DataRepository repo;

    public AddFireStationCommandImpl(DataRepository repo) {
        this.repo = repo;
    }

    /**
     * Adds a new address-to-station mapping to the repository.
     *
     * The check is intentionally non-idempotent: adding an exact duplicate
     * (same address AND same station) throws {@link DuplicateFireStationMappingException}
     *
     * @param fs the fire-station mapping to add
     * @throws DuplicateFireStationMappingException if the exact address+station pair already exists
     */
    @Override
    public void execute(FireStation fs) {
        logger.debug("Attempting to add firestation mapping address={} station={}", fs.address(), fs.station());
        repo.update(oldData -> {
            boolean alreadyExists =
                    oldData.fireStations().stream().
                            anyMatch(
                                    existing -> existing.address().equals(fs.address())
                                    && existing.station().equals(fs.station()));
            // We could have made it Idempotent, but It would have violated the business logic rule.
            if (alreadyExists) {
                logger.error("Duplicate firestation mapping: address={} station={} already exists", fs.address(), fs.station());
                throw new DuplicateFireStationMappingException(fs.address(), fs.station());
            }

            Set<FireStation> updated = new HashSet<>(oldData.fireStations());
            updated.add(fs);

            return new DataWrapper(oldData.persons(), List.copyOf(updated), oldData.medicalRecords()
            );
        });
    }
}

