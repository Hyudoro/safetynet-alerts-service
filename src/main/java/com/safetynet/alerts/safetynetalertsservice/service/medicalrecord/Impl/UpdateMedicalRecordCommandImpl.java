package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl;

import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.UpdateMedicalRecordCommand;
import org.springframework.stereotype.Service;

import java.util.List;

//we must be able to change the medications, and the allergies.
@Service
public class UpdateMedicalRecordCommandImpl implements UpdateMedicalRecordCommand {
    @Override
    public void executeRemovingMedication(String name, String firstName, List<String> medicationDel) {

    }

    @Override
    public void executeRemovingAllergy(String name, String firstName, List<String> allergyDel) {

    }

    @Override
    public void executeAddingMedication(String name, String firstName, List<String> medicationDel) {

    }

    @Override
    public void executeAddingAllergy(String name, String firstName, List<String> allergyDel) {

    }
}
