package com.safetynet.alerts.safetynetalertsservice.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DataWrapper(
        List<Person> persons,
        @JsonProperty ("firestations")
        List<FireStation> fireStations,
        @JsonProperty ("medicalrecords")
        List<MedicalRecord> medicalRecords

) {

}
