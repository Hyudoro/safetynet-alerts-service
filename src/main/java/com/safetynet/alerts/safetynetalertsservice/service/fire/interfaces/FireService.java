package com.safetynet.alerts.safetynetalertsservice.service.fire.interfaces;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.fire.FireResponseDTO;

public interface FireService {
    FireResponseDTO getResidentMedicalByAddress(String address);
}
