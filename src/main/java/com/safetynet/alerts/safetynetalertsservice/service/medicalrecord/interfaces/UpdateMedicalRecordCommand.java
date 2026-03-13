package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;

import java.util.List;

// does need a body with the medication
public interface UpdateMedicalRecordCommand {
    void executeUpdatingMedication(MedicalRecord.Id id, List<String> medication);
    void executeUpdatingAllergy(MedicalRecord.Id id, List<String> allergy);
}
