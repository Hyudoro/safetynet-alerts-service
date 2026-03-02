package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord;


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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteMedicalRecordCommandImplIntegrationTest {

    @TempDir static Path tempDir;
    @Autowired MedicalRecordService service;
    @Autowired TestableJsonDataRepository dataRepository;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        Path tempJson = tempDir.resolve("data.json");
        registry.add("data.file.path", tempJson::toString);
    }

    @BeforeEach
    void setup(){
        dataRepository.resetToJsonSeed();
    }

    @Test
    void shouldDeleteMedicalRecordCommandIntegrationTest(){
        service.deleteMedicalRecord("Boyd","Felicia");
        assertFalse(dataRepository.findAllMedicalRecords().stream().anyMatch(
                Mr -> Mr.lastName().equals("Boyd") &&
                                     Mr.firstName().equals("Felicia")
        ));
    }

    @Test
    void shouldThrowsRunTimeExceptionIfOlMedicalRecordNotFoundIntegrationTest(){
        assertThrows(OldMedicalRecordNotFoundException.class,() ->
                service.deleteMedicalRecord("rust","isLife"));
    }











}
