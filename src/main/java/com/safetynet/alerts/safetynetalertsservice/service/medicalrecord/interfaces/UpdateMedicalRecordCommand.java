package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;

import java.util.List;

// does need a body with the medication
public interface UpdateMedicalRecordCommand {
    void executeUpdatingMedication(String lastName, String firstName, List<String> medication);
    void executeUpdatingAllergy(String lastName, String firstName, List<String> allergy);
}
