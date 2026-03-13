package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    void addMedicalRecord(MedicalRecord mR);
    void deleteMedicalRecord(MedicalRecord.Id id);
    void updateMedicationMedicalRecord(MedicalRecord.Id id, List<String> medicationDel);
    void updateAllergyMedicalRecord(MedicalRecord.Id id, List<String> allergyAdd);
}
