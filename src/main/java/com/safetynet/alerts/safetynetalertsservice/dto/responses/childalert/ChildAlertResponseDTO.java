package com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.ResidentDTO;

import java.util.List;
import java.util.Map;

public record ChildAlertResponseDTO(
        Map<ChildDTO, List<ResidentDTO>> householdMembersOfAChild
) {}
