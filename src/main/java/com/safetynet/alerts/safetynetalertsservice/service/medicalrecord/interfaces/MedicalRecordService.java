package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    void addMedicalRecord(MedicalRecord mR);
    void deleteMedicalRecord(MedicalRecord mR);
    void updateRemovingAllergyMedicalRecord(String name, String firstName, List<String> AllergyDel);
    void updateRemovingMedicatinMedicalRecord(String name, String firstName, List<String> medicationDel);
    void updateAddingAllergyMedicalRecord(String name, String firstName, List<String> allergyAdd);
    void updateAddingMedicatinMedicalRecord(String name, String firstName, List<String> medicationAdd);
}
