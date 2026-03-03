package com.safetynet.alerts.safetynetalertsservice.service.medicalrecord;
import com.safetynet.alerts.safetynetalertsservice.repository.TestableJsonDataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.Impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


import static org.junit.jupiter.api.Assertions.assertTrue;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class AddMedicalRecordCommandImplIntegrationTest {
    @TempDir static Path temp;
    @Autowired
    MedicalRecordServiceImpl service;
    @Autowired
    TestableJsonDataRepository repository;
    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        Path tempJson = temp.resolve("data.json");
        registry.add("data.file.path", tempJson::toFile);
    }
    @BeforeEach
    void setUp(){
        repository.resetToJsonSeed();
    }

    @Test
    void shoudAddMedicalRecordToTheRepositoryIntegrationTest(){
        List<String> medications = new ArrayList<>(List.of("aznol:350mg","pharmacol:200mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan","peanut","shellfish"));
        MedicalRecord medicalRecord = new MedicalRecord("George",
                "monte",
                "03/06/1984",
                medications,
                allergies);
        service.addMedicalRecord(medicalRecord);
        assertTrue(repository.findAllMedicalRecords().contains(medicalRecord));

    }

    @Test
    void shouldThrowsRunTimeExceptionIfMedicalRecordAlreadyExists(){}




}
