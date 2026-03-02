package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;



public interface DeleteMedicalRecordCommand {
    void execute(String lastName, String firstName);
}
