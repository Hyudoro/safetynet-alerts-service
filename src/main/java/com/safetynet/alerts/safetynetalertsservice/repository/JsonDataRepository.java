package com.safetynet.alerts.safetynetalertsservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.UnaryOperator;
@Repository
public class JsonDataRepository implements DataRepository {
    //making sure every threads see the latest version of currentData.
    protected volatile DataWrapper currentData;
    private final ObjectMapper objectMapper;
    private final Path dataPath;

    public JsonDataRepository(ObjectMapper objectMapper, @Value("${data.file.path}") String filePath) throws IOException {
        this.objectMapper = objectMapper;
        this.dataPath = Paths.get(filePath);
        if (Files.exists(dataPath)) {
            // if the external log file already exists we use it directly
            try (InputStream iS = Files.newInputStream(dataPath)) {
                this.currentData = load(iS);
            }
        } else {
            // Bootstrap from classpath
            try (InputStream iS = getClass().getClassLoader().getResourceAsStream("data.json")) {
                if (iS == null) {
                    throw new IOException("Seed data.json not found in classpath");
                }
                this.currentData = load(iS);
                // Create directories if needed
                Files.createDirectories(dataPath.getParent());
                // Persist initial copy
                persist(this.currentData);
            }
        }
    }

    protected DataWrapper load(InputStream is) throws IOException {
        DataWrapper loaded = objectMapper.readValue(is, DataWrapper.class);

        return new DataWrapper(
                List.copyOf(loaded.persons()),
                List.copyOf(loaded.fireStations()),
                List.copyOf(loaded.medicalRecords())
        );
    }

    @Override
    public List<Person> findAllPersons() {
        return currentData.persons();
    }

    @Override
    public List<FireStation> findAllFireStations() {
        return currentData.fireStations();
    }

    @Override
    public List<MedicalRecord> findAllMedicalRecords() {
        return currentData.medicalRecords();
    }

    public synchronized void update(UnaryOperator<DataWrapper> updateWrapper) {
        DataWrapper oldData = currentData;
        DataWrapper newData = updateWrapper.apply(oldData);
        currentData = newData;
        persist(newData);
    }

    protected void persist(DataWrapper data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(dataPath.toFile(), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to persist data", e);
        }
    }
}