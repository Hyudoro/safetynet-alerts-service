package com.safetynet.alerts.safetynetalertsservice.repository;
import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;

import java.util.List;
import java.util.function.UnaryOperator;

public interface DataRepository {
    List<Person> findAllPersons();
    List<FireStation> findAllFireStations();
    List<MedicalRecord> findAllMedicalRecords();
    void update(UnaryOperator<DataWrapper> updateWrapper);
}
