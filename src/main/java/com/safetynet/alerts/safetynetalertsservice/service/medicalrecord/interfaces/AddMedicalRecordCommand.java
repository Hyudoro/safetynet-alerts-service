package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces;

import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;

public interface AddMedicalRecordCommand {
    void execute(MedicalRecord mR);
}
