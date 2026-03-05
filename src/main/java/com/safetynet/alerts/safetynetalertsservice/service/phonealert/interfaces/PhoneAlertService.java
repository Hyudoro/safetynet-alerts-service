package com.safetynet.alerts.safetynetalertsservice.service.phonealert.interfaces;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.phonealert.PhoneAlertResponseDTO;

public interface PhoneAlertService {
    PhoneAlertResponseDTO getPhonesByStation(String StationNumber);
}
