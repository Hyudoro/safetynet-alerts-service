package com.safetynet.alerts.safetynetalertsservice.model;
import java.util.List;

public record DataWrapper(

        List<Person> persons,
        List<FireStation> fireStations,
        List<MedicalRecord> medicalrecords

) {}
