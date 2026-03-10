package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    void addMedicalRecord(MedicalRecord mR);
    void deleteMedicalRecord(String lastName, String firstName);
    void updateMedicationMedicalRecord(String firstName, String lastName, List<String> medicationDel);
    void updateAllergyMedicalRecord(String firstName, String lastName, List<String> allergyAdd);
}
