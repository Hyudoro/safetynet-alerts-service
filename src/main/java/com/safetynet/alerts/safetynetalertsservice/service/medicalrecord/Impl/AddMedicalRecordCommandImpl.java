package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateMedicalRecordMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.AddMedicalRecordCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class AddMedicalRecordCommandImpl implements AddMedicalRecordCommand {
    private static final Logger logger = LogManager.getLogger(AddMedicalRecordCommandImpl.class);

    private final DataRepository repository;

    public AddMedicalRecordCommandImpl(DataRepository repository) {
        this.repository = repository;
    }


    /**
     * Adds a new medical record to the repository.
     *
     * Identity is determined by the (firstName,lastName) pair. Adding a record whose
     * full name already exists throws {@link DuplicateMedicalRecordMappingException}.
     *
     * @param mR the medical record to add
     * @throws DuplicateMedicalRecordMappingException if a record with the same firstName+lastName already exists
     */
    @Override
    public void execute(MedicalRecord mR) {
        logger.debug("Attempting to add medical record lastName={} firstName={}", mR.lastName(), mR.firstName());
        repository.update(oldData ->{
            boolean alreadyExist = oldData.medicalRecords().stream().anyMatch(
                        oldMr -> oldMr.firstName().equals(mR.firstName()) &&
                                                oldMr.lastName().equals(mR.lastName()));
            if(alreadyExist){
                logger.error("Duplicate medical record: lastName={} firstName={} already exists", mR.lastName(), mR.firstName());
                throw new DuplicateMedicalRecordMappingException(mR.firstName(), mR.lastName());
            }

            Set<MedicalRecord> MedicalRecords = new HashSet <>(oldData.medicalRecords());
            MedicalRecords.add(mR);

            return new DataWrapper(oldData.persons(),oldData.fireStations(),List.copyOf(MedicalRecords));

        });
    }
}
