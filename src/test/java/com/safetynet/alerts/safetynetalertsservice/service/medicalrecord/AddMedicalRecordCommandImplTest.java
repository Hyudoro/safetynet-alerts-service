package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateMedicalRecordMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl.AddMedicalRecordCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl.MedicalRecordServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.DeleteMedicalRecordCommand;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.UpdateMedicalRecordCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddMedicalRecordCommandImplTest {
    @Mock DeleteMedicalRecordCommand deleteMedicalRecordCommand;
    @Mock UpdateMedicalRecordCommand updateMedicalRecordCommand;
    @Mock DataRepository  dataRepository;

    private MedicalRecordServiceImpl service;

    @BeforeEach
    public void setUp() {
        AddMedicalRecordCommandImpl addMedicalRecordCommandImpl = new AddMedicalRecordCommandImpl(dataRepository);
        service = new MedicalRecordServiceImpl(addMedicalRecordCommandImpl, deleteMedicalRecordCommand, updateMedicalRecordCommand);
    }

    @Test
    void shouldAddMedicalRecordCommandTest() {
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);

            DataWrapper oldDataWrapper = new DataWrapper(List.of(),List.of(),List.of());
            DataWrapper newDataWrapper = lambda.apply(oldDataWrapper);
            MedicalRecord newAddedMedicalRecord = newDataWrapper.medicalRecords().getFirst();
            assertTrue(newDataWrapper.medicalRecords().contains(newAddedMedicalRecord));
            return null;
        }).when(dataRepository).update(any());
        List<String> medications = new ArrayList<>(List.of("aznol:350mg","pharmacol:200mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan","peanut","shellfish"));

        service.addMedicalRecord(new MedicalRecord(
                "George",
                "monte",
                "03/06/1984",
                medications,
                allergies));
    }

    @Test
    void shouldThrowsRunTimeExceptionIfMedicalRecordAlreadyExistsTest() {
        List<String> oldMedications = new ArrayList<>(List.of("aznol:350mg","pharmacol:200mg"));
        List<String> oldAllergies = new ArrayList<>(List.of("nillacilan","peanut","shellfish"));
        doAnswer(invocation ->{
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            List<String> medications = new ArrayList<>(List.of("aznol:350mg","pharmacol:200mg"));
            List<String> allergies = new ArrayList<>(List.of("nillacilan","peanut","shellfish"));
            MedicalRecord medicalRecord = new MedicalRecord("George",
                    "monte",
                    "03/06/1984",
                    medications,
                    allergies);
            List<MedicalRecord> medicalRecords = new ArrayList<>(List.of(medicalRecord));
            return lambda.apply(new DataWrapper(List.of(),List.of(),List.copyOf(medicalRecords)));
        }).when(dataRepository).update(any());

        assertThrows(DuplicateMedicalRecordMappingException.class, () -> service.addMedicalRecord(new MedicalRecord("George",
                "monte",
                "03/06/1984",
                oldMedications,
                oldAllergies)));

    }
}
