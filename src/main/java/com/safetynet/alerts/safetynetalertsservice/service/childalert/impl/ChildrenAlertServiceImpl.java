package com.safetynet.alerts.safetynetalertsservice.service.childalert.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildWithHouseHoldMembersDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildrenAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.HouseHoldMemberDTO;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.interfaces.ChildrenAlertService;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

/**
 * Returns the list of children living at a given address together with
 * the other household members who share the same last name.
 */
@Service
public class ChildrenAlertServiceImpl implements ChildrenAlertService {
    private static final Logger logger = LogManager.getLogger(ChildrenAlertServiceImpl.class);
    private final DataRepository repository;

    public ChildrenAlertServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns all children at {@code address} paired with their household members
     * First pass filter persons at the address who have a medical record, then
     * collect those whose calculated age is below 18 into {@code children}.
     * If no children are found, return an empty response immediately.
     * Second pass for each child, scan the same person list for anyone sharing
     * the same last name but a different first name.
     *
     *
     * @param address the street address to query
     * @return a response containing each child with their household members,
     *         or an empty list if no child lives at that address
     */
    @Override
    public ChildrenAlertResponseDTO getChildrenAndTheirHouseHoldMembersByAddress(String address) {
        List<Person> personsAtAddress = repository.findAllPersons().stream()
                .filter(p -> p.address().equals(address) &&
                        repository.findAllMedicalRecords().stream()
                                .anyMatch(mR -> p.firstName().equals(mR.firstName()) &&
                                        p.lastName().equals(mR.lastName())))
                .toList();
        logger.debug("{} persons with medical records found at address={}", personsAtAddress.size(), address);
        List<ChildDTO> children = new ArrayList<>();

        for (Person p : personsAtAddress) {
            repository.findAllMedicalRecords().stream()
                    .filter(mR -> p.firstName().equals(mR.firstName()) && p.lastName().equals(mR.lastName()))
                    .findFirst()
                    .ifPresent(mR -> {
                        int age = AgeCalculator.calculate(mR.birthDate());
                        if (age < 18) {
                            children.add(new ChildDTO(p.lastName(), p.firstName(), age));
                        }
                    });
        }

        logger.debug("{} children identified at address={}", children.size(), address);
        if (children.isEmpty()){
            return new ChildrenAlertResponseDTO(List.of());
        }
        List<ChildWithHouseHoldMembersDTO>  childWithHouseHoldMembers = new ArrayList<>();

        for(ChildDTO child : children){
            List<HouseHoldMemberDTO> houseHoldMembers = new ArrayList<>();
            for(Person p : personsAtAddress){
                if(child.lastName().equals(p.lastName()) && !child.firstName().equals(p.firstName())){
                    HouseHoldMemberDTO houseHoldMemberDTO = new HouseHoldMemberDTO(
                                                                                    p.lastName(),
                                                                                    p.firstName(),
                                                                                    p.phone(),
                                                                                    p.address(),
                                                                                    p.city());
                    houseHoldMembers.add(houseHoldMemberDTO);
                }
            }
            logger.debug("Child {} {}: {} household members found", child.firstName(), child.lastName(), houseHoldMembers.size());
            childWithHouseHoldMembers.add(new ChildWithHouseHoldMembersDTO(child.firstName(),child.lastName(),child.age(), houseHoldMembers));
        }
        return new ChildrenAlertResponseDTO(childWithHouseHoldMembers);
    } //complexity O(n)^2, probably better with a keyMapper we could have O(n).
}
