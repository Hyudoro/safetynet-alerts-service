package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;


import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.AddMedicalRecordCommand;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.DeleteMedicalRecordCommand;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.UpdateMedicalRecordCommand;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final AddMedicalRecordCommand addCommand;
    private final DeleteMedicalRecordCommand deleteCommand;
    private final UpdateMedicalRecordCommand updateCommand;

    public MedicalRecordServiceImpl(AddMedicalRecordCommand addMedicalRecordCommand,
                                    DeleteMedicalRecordCommand deleteMedicalRecordCommand,
                                    UpdateMedicalRecordCommand updateMedicalRecordCommand)
    {
        this.addCommand = addMedicalRecordCommand;
        this.deleteCommand = deleteMedicalRecordCommand;
        this.updateCommand = updateMedicalRecordCommand;
    }
    @Override
    public void addMedicalRecord(MedicalRecord mR) {
         addCommand.execute(mR);
    }

    @Override
    public void deleteMedicalRecord(MedicalRecord.Id id) {
         deleteCommand.execute(id);
    }

    @Override
    public void updateMedicationMedicalRecord(MedicalRecord.Id id, List<String> medication) {
        updateCommand.executeUpdatingMedication(id, medication);
    }

    @Override
    public void updateAllergyMedicalRecord(MedicalRecord.Id id, List<String> allergy) {
        updateCommand.executeUpdatingAllergy(id, allergy);
    }
}
