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
    public void deleteMedicalRecord(String lastName, String firstName) {
         deleteCommand.execute(lastName,firstName);
    }

    @Override
    public void updateMedicationMedicalRecord(String lastName, String firstName, List<String> medication) {
        updateCommand.executeUpdatingMedication(lastName, firstName,medication);
    }

    @Override
    public void updateAllergyMedicalRecord(String lastName, String firstName, List<String> allergy) {
        updateCommand.executeUpdatingAllergy(lastName, firstName,allergy);
    }
}
