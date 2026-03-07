package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateMedicalRecordMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.AddMedicalRecordCommand;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class AddMedicalRecordCommandImpl implements AddMedicalRecordCommand {

    private final DataRepository repository;

    public AddMedicalRecordCommandImpl(DataRepository repository) {
        this.repository = repository;
    }


    @Override
    public void execute(MedicalRecord mR) {
        repository.update(oldData ->{
            boolean alreadyExist = oldData.medicalRecords().stream().anyMatch(
                        oldMr -> oldMr.firstName().equals(mR.firstName()) &&
                                                oldMr.lastName().equals(mR.lastName()));
            if(alreadyExist){
                throw new DuplicateMedicalRecordMappingException(mR.firstName(), mR.lastName());
            }

            Set<MedicalRecord> MedicalRecords = new HashSet <>(oldData.medicalRecords());
            MedicalRecords.add(mR);

            return new DataWrapper(oldData.persons(),oldData.fireStations(),List.copyOf(MedicalRecords));

        });
    }
}
