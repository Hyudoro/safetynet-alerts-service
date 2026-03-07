package com.safetynet.alerts.safetynetalertsservice.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;

import java.util.List;

public record DataWrapper(
        List<Person> persons,
        @JsonProperty ("firestations")
        List<FireStation> fireStations,
        @JsonProperty ("medicalrecords")
        List<MedicalRecord> medicalRecords

) {

}
