package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;


import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.UpdateMedicalRecordCommand;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//we must be able to change the medications, and the allergies.
@Service
public class UpdateMedicalRecordCommandImpl implements UpdateMedicalRecordCommand {
    private final DataRepository repository;

    public UpdateMedicalRecordCommandImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeUpdatingMedication(String lastName, String firstName, List<String> medication) {
        repository.update(oldData -> {
            Set<MedicalRecord> currentData = new HashSet<>(oldData.medicalRecords());

            currentData.stream()
                    .filter(mr -> mr.lastName().equals(lastName) && mr.firstName().equals(firstName))
                    .findFirst()
                    .ifPresentOrElse(mr -> {
                                mr.medications().clear();
                                mr.medications().addAll(medication);
                            },
                            () -> { throw new OldMedicalRecordNotFoundException(lastName, firstName); });

            return new DataWrapper(oldData.persons(), oldData.fireStations(), List.copyOf(currentData));
        });
    }

    @Override
    public void executeUpdatingAllergy(String lastName, String firstName, List<String> allergy) {
        repository.update(oldData -> {
            Set<MedicalRecord> currentData = new HashSet<>(oldData.medicalRecords());

            currentData.stream()
                    .filter(mr -> mr.lastName().equals(lastName) && mr.firstName().equals(firstName))
                    .findFirst()
                    .ifPresentOrElse(mr -> {
                                mr.allergies().clear();
                                mr.allergies().addAll(allergy);
                            },
                            () -> { throw new OldMedicalRecordNotFoundException(lastName, firstName); });

            return new DataWrapper(oldData.persons(), oldData.fireStations(), List.copyOf(currentData));
        });
    }
}
