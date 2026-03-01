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
    public void deleteMedicalRecord(MedicalRecord mR) {
         deleteCommand.execute(mR);
    }

    @Override
    public void updateRemovingAllergyMedicalRecord(String name, String firstName, List<String> allergyDel) {
         updateCommand.executeRemovingAllergy(name, firstName, allergyDel);
    }

    @Override
    public void updateAddingAllergyMedicalRecord(String name, String firstName, List<String> allergyAdd) {
         updateCommand.executeAddingAllergy(name, firstName, allergyAdd);
    }

    @Override
    public void updateRemovingMedicatinMedicalRecord(String name, String firstName, List<String> medicationDel) {
         updateCommand.executeRemovingMedication(name, firstName, medicationDel);
    }

    @Override
    public void updateAddingMedicatinMedicalRecord(String name, String firstName, List<String> medicationAdd) {
         updateCommand.executeAddingMedication(name, firstName, medicationAdd);
    }
}
