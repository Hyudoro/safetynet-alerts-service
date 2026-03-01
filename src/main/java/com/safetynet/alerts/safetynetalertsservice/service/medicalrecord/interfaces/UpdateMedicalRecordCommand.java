package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;

import java.util.List;

// does need a body with the medication
public interface UpdateMedicalRecordCommand {
    void executeRemovingMedication(String name, String firstName, List<String> medicationDel);
    void executeRemovingAllergy(String name, String firstName, List<String> allergyDel);
    void executeAddingMedication(String name, String firstName, List<String> medicationDel);
    void executeAddingAllergy(String name, String firstName , List<String> allergyDel);
}
