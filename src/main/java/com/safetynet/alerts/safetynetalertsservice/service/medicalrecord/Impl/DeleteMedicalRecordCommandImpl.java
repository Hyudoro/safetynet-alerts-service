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

    @Override
    public void execute(String lastName, String firstName) {
        repository.update(oldData -> { //possible optimization (if I have time : theme : key projection)
            Set<MedicalRecord> oldMedicalRecords = new HashSet<>(oldData.medicalRecords());
            boolean removed = oldMedicalRecords.removeIf(medicalRecord ->
                    medicalRecord.firstName().equals(firstName) &&
                    medicalRecord.lastName().equals(lastName)
            );

            if (!removed) {
                throw new OldMedicalRecordNotFoundException(lastName, firstName);
            }
            
            return new DataWrapper(oldData.persons(),oldData.fireStations(),List.copyOf(oldMedicalRecords));
        });
    }
}
