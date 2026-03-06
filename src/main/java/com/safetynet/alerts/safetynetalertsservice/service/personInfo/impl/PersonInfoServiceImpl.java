package com.safetynet.alerts.safetynetalertsservice.service.personInfo.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.personinfo.PersonInfoDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.personinfo.PersonInfoResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FullName;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalHistory;

import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.personInfo.interfaces.PersonInfoService;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    private final DataRepository repository;

    public PersonInfoServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public PersonInfoResponseDTO getPersonInfo(String lastName) {
        Map<FullName, MedicalHistory> PersonMeds =
                repository.findAllMedicalRecords().stream().filter(
                        mR -> lastName.equals(mR.lastName()))
                        .collect(Collectors.toMap(
                                key -> new FullName(key.lastName(),key.firstName()),
                                               value -> new MedicalHistory(
                                                       value.medications(),value.allergies(), AgeCalculator.calculate(value.birthDate()))
                                ,(existing, replacement) -> existing

                        ));

        List<PersonInfoDTO> value = repository.findAllPersons().stream().
                filter(p-> lastName.equals(p.lastName()))
                .map(p-> {
                    MedicalHistory meds = PersonMeds.get(new FullName(p.lastName(),p.firstName()));
                    return new PersonInfoDTO(
                            p.firstName(),
                            p.lastName(),
                            p.address(),
                            meds.age(),
                            p.email(),
                            meds.medications(),
                            meds.allergies()
                    );}).toList();
        return new  PersonInfoResponseDTO(value);
    }
}
