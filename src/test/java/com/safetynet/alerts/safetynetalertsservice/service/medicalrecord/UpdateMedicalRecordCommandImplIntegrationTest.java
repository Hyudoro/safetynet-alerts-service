package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord;

import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.TestableJsonDataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("test")
public class UpdateMedicalRecordCommandImplIntegrationTest {
    @TempDir static Path temp;
    @Autowired private MedicalRecordService service;
    @Autowired TestableJsonDataRepository repository;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        Path tempJson = temp.resolve("data");
        registry.add("data.file.path", tempJson::toString);
    }

    @BeforeEach
    void setup(){
        repository.resetToJsonSeed();
    }

    @Test
    void shouldUpdateMedicalRecordMedicationIntegrationTest(){
        service.updateMedicationMedicalRecord("Cooper","Lily", new ArrayList<>(List.of("paracetamol:300mg")));
        MedicalRecord updatedMedicalRecord = repository.findAllMedicalRecords()
                .stream()
                .filter(mR -> mR.lastName().equals("Cooper") && mR.firstName().equals("Lily"))
                .findFirst()
                .orElseThrow();
        assertThat(updatedMedicalRecord.medications()).containsExactly("paracetamol:300mg");
    }

    @Test
    void shouldUpdateMedicalRecordAllergyIntegrationTest(){
        service.updateAllergyMedicalRecord("Cooper","Lily", new ArrayList<>(List.of("butter")));
        MedicalRecord updatedMedicalRecord = repository.findAllMedicalRecords()
                .stream()
                .filter(mR -> mR.lastName().equals("Cooper") && mR.firstName().equals("Lily"))
                .findFirst()
                .orElseThrow();
        assertThat(updatedMedicalRecord.allergies()).containsExactly("butter");
    }

    @Test
    void shouldReturnRuntimeExceptionIfMedicalRecordNotFoundUpdatingMedicationIntegrationTest(){
        assertThatThrownBy(()-> service.updateMedicationMedicalRecord(
                "Lenon","Jhon",new ArrayList<>(List.of("paracetamol:300mg"))))
                            .isInstanceOf(OldMedicalRecordNotFoundException.class);
    }

    @Test
    void shouldReturnRuntimeExceptionIfMedicalRecordNotFoundUpdatingAllergyIntegrationTest(){
        assertThatThrownBy(()-> service.updateAllergyMedicalRecord(
                "Lenon","Jhon",new ArrayList<>(List.of("butter"))))
                .isInstanceOf(OldMedicalRecordNotFoundException.class);
    }








}
