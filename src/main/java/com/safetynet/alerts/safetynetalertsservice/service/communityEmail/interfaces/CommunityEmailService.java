package com.safetynet.alerts.safetynetalertsservice.service.communityEmail.interfaces;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.communityemail.CommunityEmailResponseDTO;

public interface CommunityEmailService {
    CommunityEmailResponseDTO getCommunityEmail(String city);
}
