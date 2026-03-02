package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl.DeleteMedicalRecordCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl.MedicalRecordServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.AddMedicalRecordCommand;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteMedicalRecordCommandImplTest {
    @Mock AddMedicalRecordCommand addMedicalRecordCommand;
    @Mock UpdateMedicalRecordCommand updateMedicalRecordCommand;
    @Mock DataRepository repository;
    private MedicalRecordServiceImpl service;

    @BeforeEach
    public void setUp(){
        DeleteMedicalRecordCommandImpl deleteMedicalRecordCommandImpl = new DeleteMedicalRecordCommandImpl(repository);
        service = new MedicalRecordServiceImpl(addMedicalRecordCommand,deleteMedicalRecordCommandImpl,updateMedicalRecordCommand);
    }

    @Test
    void shouldDeleteMedicalRecordCommandTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            List<String> medications = new ArrayList<>(List.of("aznol:350mg","pharmacol:200mg"));
            List<String> allergies = new ArrayList<>(List.of("nillacilan","peanut","shellfish"));
            MedicalRecord medicalRecord = new MedicalRecord(
                    "George",
                    "monte",
                    "03/06/1984",
                    medications,
                    allergies);
            List<MedicalRecord> medicalRecords = new ArrayList<>(List.of(medicalRecord));
            DataWrapper newDataWrapper = lambda.apply(new DataWrapper(List.of(),List.of(),List.copyOf(medicalRecords)));
            assertTrue(newDataWrapper.medicalRecords().isEmpty());

            return null;
        }).when(repository).update(any());
        service.deleteMedicalRecord("monte","George");
    }

    @Test
    void shouldThrowsRunTimeExceptionTest(){
        doAnswer(invocation -> { //reminder I should simplify other tests.
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            return lambda.apply(new DataWrapper(List.of(),List.of(),List.of()));
        }).when(repository).update(any());
        assertThrows(OldMedicalRecordNotFoundException.class, () -> service.deleteMedicalRecord("monte","George"));
    }
}
