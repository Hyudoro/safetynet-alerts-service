package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl.MedicalRecordServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl.UpdateMedicalRecordCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.AddMedicalRecordCommand;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.DeleteMedicalRecordCommand;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
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
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class UpdateMedicalRecordCommandTest {
    @Mock AddMedicalRecordCommand addMedicalRecordCommand;
    @Mock DeleteMedicalRecordCommand deleteMedicalRecordCommand;
    @Mock DataRepository dataRepository;

    private MedicalRecordService service;

    @BeforeEach
    public void setUp(){
        UpdateMedicalRecordCommand updateMedicalRecordCommand = new UpdateMedicalRecordCommandImpl(dataRepository);
        service = new MedicalRecordServiceImpl(addMedicalRecordCommand, deleteMedicalRecordCommand, updateMedicalRecordCommand);
    }

    @Test
    void shouldUpdateMedicalRecordMedicationCommandTest(){
    doAnswer(invocation -> {
        UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
        List<String> medications = new ArrayList<>(List.of("aznol:350mg","pharmacol:200mg"));
        List<String> allergies = new ArrayList<>();
        MedicalRecord medicalRecord = new MedicalRecord(
                "George",
                "monte",
                "",
                medications,
                allergies);

        List<MedicalRecord> medicalRecords = new ArrayList<>(List.of(medicalRecord));
        DataWrapper oldData = new DataWrapper(List.of(),List.of(),medicalRecords);
        lambda.apply(oldData);
        assertTrue(oldData.medicalRecords().stream()
                .anyMatch(r -> r.medications().contains("anzol:300mg")));
        return null;
    }).when(dataRepository).update(any());

    service.updateMedicationMedicalRecord("monte","George", new ArrayList<>(List.of("anzol:300mg", "paracetamol:200mg")));

    }

    @Test
    void shouldReturnRuntimeExceptionIfMedicalRecordNotFoundUpdatingMedicationTest(){
        doAnswer(invocationOnMock ->  {
            UnaryOperator<DataWrapper> lambda = invocationOnMock.getArgument(0);
            return lambda.apply(new DataWrapper(List.of(),List.of(),List.of()));
        }).when(dataRepository).update(any());

        assertThrows(OldMedicalRecordNotFoundException.class, () -> service.updateMedicationMedicalRecord(
                "monte","George", new ArrayList<>(List.of("anzol:300mg", "paracetamol:200mg"))));
    }

    @Test
    void shouldUpdateMedicalRecordAllergyCommandTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            List<String> medications = new ArrayList<>();
            List<String> allergies = new ArrayList<>(List.of("nillacilan","peanut","shellfish"));
            MedicalRecord medicalRecord = new MedicalRecord(
                    "George",
                    "monte",
                    "",
                    medications,
                    allergies);

            List<MedicalRecord> medicalRecords = new ArrayList<>(List.of(medicalRecord));
            DataWrapper oldData = new DataWrapper(List.of(),List.of(),medicalRecords);
            lambda.apply(oldData);
            assertTrue(oldData.medicalRecords().stream()
                    .anyMatch(mR -> mR.allergies().contains("ananas")));
            return null;
        }).when(dataRepository).update(any());

        service.updateAllergyMedicalRecord("monte","George", new ArrayList<>(List.of("ananas")));

    }

    @Test
    void shouldReturnRuntimeExceptionIfMedicalRecordNotFountUpdatingAllergyTest(){
        doAnswer(invocationOnMock ->  {
            UnaryOperator<DataWrapper> lambda = invocationOnMock.getArgument(0);
            return lambda.apply(new DataWrapper(List.of(),List.of(),List.of()));
        }).when(dataRepository).update(any());

        assertThrows(OldMedicalRecordNotFoundException.class, () -> service.updateAllergyMedicalRecord(
                "monte","George", new ArrayList<>(List.of("ananas"))));
    }

}
