package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;


import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.DeleteMedicalRecordCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// (name,firstname) are the identifiers
@Service
public class DeleteMedicalRecordCommandImpl implements DeleteMedicalRecordCommand {
    private static final Logger logger = LogManager.getLogger(DeleteMedicalRecordCommandImpl.class);
    private final DataRepository repository;

    public DeleteMedicalRecordCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Removes the medical record identified by {@code id} (firstName+lastName) from the repository.
     *
     * @param id the firstName+lastName key identifying the record to delete
     * @throws OldMedicalRecordNotFoundException if no record with {@code id} exists
     */
    @Override
    public void execute(MedicalRecord.Id id) {
        logger.debug("Attempting to delete medical record lastName={} firstName={}", id.lastName(), id.firstName());
        repository.update(oldData -> { //possible optimization (if I have time : theme : key projection)
            Set<MedicalRecord> oldMedicalRecords = new HashSet<>(oldData.medicalRecords());
            boolean removed = oldMedicalRecords.removeIf(medicalRecord ->
                    medicalRecord.firstName().equals(id.firstName()) &&
                    medicalRecord.lastName().equals(id.lastName())
            );

            if (!removed) {
                logger.error("Medical record not found: lastName={} firstName={}", id.lastName(), id.firstName());
                throw new OldMedicalRecordNotFoundException(id.lastName(), id.firstName());
            }
            
            return new DataWrapper(oldData.persons(),oldData.fireStations(),List.copyOf(oldMedicalRecords));
        });
    }
}
