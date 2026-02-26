package com.safetynet.alerts.safetynetalertsservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

/**
 * Test-only subclass of JsonDataRepository.
 * Provides a resetToJsonSeed() method to restore the initial JSON repo
 */
@Profile("test")
@Repository
@Primary
public class TestableJsonDataRepository extends JsonDataRepository {

    public TestableJsonDataRepository(ObjectMapper objectMapper, @Value("${data.file.path}") String filePath) throws IOException {
        super(objectMapper, filePath);
    }
    public synchronized void resetToJsonSeed() {
        try (InputStream iS = getClass().getClassLoader().getResourceAsStream("data.json")) {
            if (iS == null) {
                throw new IOException("Seed data.json not found in classpath");
            }
            DataWrapper seed = load(iS);
            currentData = seed;
            persist(seed);
        } catch (IOException e) {
            throw new RuntimeException("Failed to reset repository", e);
        }
    }
}