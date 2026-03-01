package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;

import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.DeleteMedicalRecordCommand;
import org.springframework.stereotype.Service;

// (name,firstname) are the identifiers
@Service
public class DeleteMedicalRecordCommandImpl implements DeleteMedicalRecordCommand {
    @Override
    public void execute(MedicalRecord mR) {

    }
}
