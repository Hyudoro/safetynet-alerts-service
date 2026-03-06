package com.safetynet.alerts.safetynetalertsservice.service.personInfo.interfaces;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.personinfo.PersonInfoResponseDTO;

public interface PersonInfoService {
    PersonInfoResponseDTO getPersonInfo(String lastName);
}
