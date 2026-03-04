package com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert;



import java.util.List;

public record ChildrenAlertResponseDTO(
        List<ChildWithHouseHoldMembersDTO> childAndHouseHoldMembers
) {}
