package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;


import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
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

    /**
     * Replaces the medication list of the record identified by {@code id}.
     *
     * The list is mutated in-place via {@code clear()} + {@code addAll()} so that
     * any other references to the same list object also see the update.
     *
     * @param id the firstName+lastName key identifying the record to update
     * @param medication the new medication list (replaces the existing one entirely)
     * @throws OldMedicalRecordNotFoundException if no record with {@code id} exists
     */
    @Override
    public void executeUpdatingMedication(MedicalRecord.Id id, List<String> medication) {
        repository.update(oldData -> {
            Set<MedicalRecord> currentData = new HashSet<>(oldData.medicalRecords());

            currentData.stream()
                    .filter(mr -> mr.firstName().equals(id.firstName()) && mr.lastName().equals(id.lastName()))
                    .findFirst()
                    .ifPresentOrElse(mr -> {
                                mr.medications().clear();
                                mr.medications().addAll(medication);
                            },
                            () -> { throw new OldMedicalRecordNotFoundException(id.lastName(), id.firstName()); });

            return new DataWrapper(oldData.persons(), oldData.fireStations(), List.copyOf(currentData));
        });
    }

    /**
     * Replaces the allergy list of the record identified by {@code id}.
     *
     * The list is mutated in-place via {@code clear()} + {@code addAll()} so that
     * any other references to the same list object also see the update.
     *
     * @param id      the firstName+lastName key identifying the record to update
     * @param allergy the new allergy list (replaces the existing one entirely)
     * @throws OldMedicalRecordNotFoundException if no record with {@code id} exists
     */
    @Override
    public void executeUpdatingAllergy(MedicalRecord.Id id, List<String> allergy) {
        repository.update(oldData -> {
            Set<MedicalRecord> currentData = new HashSet<>(oldData.medicalRecords());

            currentData.stream()
                    .filter(mr -> mr.firstName().equals(id.firstName()) && mr.lastName().equals(id.lastName()))
                    .findFirst()
                    .ifPresentOrElse(mr -> {
                                mr.allergies().clear();
                                mr.allergies().addAll(allergy);
                            },
                            () -> { throw new OldMedicalRecordNotFoundException(id.lastName(), id.firstName()); });

            return new DataWrapper(oldData.persons(), oldData.fireStations(), List.copyOf(currentData));
        });
    }
}
