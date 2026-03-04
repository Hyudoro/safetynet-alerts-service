package com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert;

import java.util.List;

public record ChildWithHouseHoldMembersDTO(
        String firstName,
        String lastName,
        int age,
        List<HouseHoldMemberDTO> houseHoldMembers
) {}

