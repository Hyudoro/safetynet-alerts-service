package com.safetynet.alerts.safetynetalertsservice.repository;


import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.UnaryOperator;

@Repository
public class JsonDataRepository implements DataRepository {
            //making sure every threads see the latest version of currentData.
    private volatile DataWrapper currentData;
    private final ObjectMapper objectMapper;
    private final Path dataPath = Paths.get("src/main/resources/data.json");

    public JsonDataRepository(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        if (inputStream == null) {
            throw new IllegalStateException("data.json not found");
        }
        DataWrapper loaded = objectMapper.readValue(inputStream, DataWrapper.class);

        this.currentData = new DataWrapper(
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

    private void persist(DataWrapper data) {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(dataPath.toFile(), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to persist data", e);
        }
    }




}