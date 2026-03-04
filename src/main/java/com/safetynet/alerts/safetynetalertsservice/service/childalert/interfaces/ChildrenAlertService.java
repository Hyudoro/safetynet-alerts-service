package com.safetynet.alerts.safetynetalertsservice.service.childalert.interfaces;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildrenAlertResponseDTO;

public interface ChildrenAlertService {
   ChildrenAlertResponseDTO getChildrenAndTheirHouseHoldMembersByAddress(String address);
}
