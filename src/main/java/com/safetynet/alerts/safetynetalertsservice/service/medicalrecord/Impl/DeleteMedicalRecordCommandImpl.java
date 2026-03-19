package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;


import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.DeleteMedicalRecordCommand;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// (name,firstname) are the identifiers
@Service
public class DeleteMedicalRecordCommandImpl implements DeleteMedicalRecordCommand {
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
        repository.update(oldData -> { //possible optimization (if I have time : theme : key projection)
            Set<MedicalRecord> oldMedicalRecords = new HashSet<>(oldData.medicalRecords());
            boolean removed = oldMedicalRecords.removeIf(medicalRecord ->
                    medicalRecord.firstName().equals(id.firstName()) &&
                    medicalRecord.lastName().equals(id.lastName())
            );

            if (!removed) {
                throw new OldMedicalRecordNotFoundException(id.lastName(), id.firstName());
            }
            
            return new DataWrapper(oldData.persons(),oldData.fireStations(),List.copyOf(oldMedicalRecords));
        });
    }
}
