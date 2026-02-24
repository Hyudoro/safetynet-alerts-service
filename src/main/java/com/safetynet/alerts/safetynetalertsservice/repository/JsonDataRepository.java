package com.safetynet.alerts.safetynetalertsservice.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//deserialization
@Repository
@JsonIgnoreProperties(ignoreUnknown = true) // That way, we are not bothered if the JSON has more properties.
public class JsonDataRepository implements DataRepository{
    private final List<Person> persons;
    private final List<FireStation> fireStations;
    private final List<MedicalRecord> medicalRecords;

    public JsonDataRepository(ObjectMapper objectMapper) throws IOException, NullPointerException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        DataWrapper wrapper = objectMapper.readValue(inputStream, DataWrapper.class);
        if (inputStream == null){
            throw new IllegalStateException("data.json not found");
        }
        this.persons = List.copyOf(wrapper.persons());
        this.fireStations = List.copyOf(wrapper.fireStations());
        this.medicalRecords = List.copyOf(wrapper.medicalRecords());
    }

    @Override
    public List<Person> findAllPersons() {
        return persons;
    }

    @Override
    public List<FireStation> findAllFireStations() {
        return fireStations;
    }

    @Override
    public List<MedicalRecord> findAllMedicalRecords() {
        return medicalRecords;
    }

}
