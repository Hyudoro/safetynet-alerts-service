package com.safetynet.alerts.safetynetalertsservice.repository;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;

import java.util.List;

public interface DataRepository {
    List<Person> findAllPersons();
    List<FireStation> findAllFirestations();
    List<MedicalRecord> findAllMedicalRecords();
}
