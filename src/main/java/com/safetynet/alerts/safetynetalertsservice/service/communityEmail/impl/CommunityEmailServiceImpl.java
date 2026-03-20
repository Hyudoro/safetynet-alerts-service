package com.safetynet.alerts.safetynetalertsservice.service.communityEmail.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.communityemail.CommunityEmailResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.communityEmail.interfaces.CommunityEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Collects the email addresses of every person registered in the given city.
 */
@Service
public class CommunityEmailServiceImpl implements CommunityEmailService {
    private static final Logger logger = LogManager.getLogger(CommunityEmailServiceImpl.class);
    private final DataRepository repository;


    public CommunityEmailServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns the email addresses of all persons whose registered city equals {@code city}.
     *
     * @param city the city name to filter on (case-sensitive)
     * @return a response containing the matching email addresses
     */
    @Override
    public CommunityEmailResponseDTO getCommunityEmail(String city) {
        List<String> Emails = repository.findAllPersons().stream().
                filter(p-> city.equals(p.city())).
                map(Person::email).toList();
        logger.debug("{} emails found for city={}", Emails.size(), city);
        return new CommunityEmailResponseDTO(Emails);
    }
}
