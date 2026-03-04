package com.safetynet.alerts.safetynetalertsservice.service.childalert.impl;

import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildWithHouseHoldMembersDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildrenAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.HouseHoldMemberDTO;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.interfaces.ChildrenAlertService;
import com.safetynet.alerts.safetynetalertsservice.util.AgeCalculator;

import java.util.ArrayList;

import java.util.List;

public class ChildrenAlertServiceImpl implements ChildrenAlertService {
    private final DataRepository repository;

    public ChildrenAlertServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChildrenAlertResponseDTO getChildrenAndTheirHouseHoldMembersByAddress(String address) {

        List<Person> personsAtAddress = repository.findAllPersons().stream()
                .filter(p -> p.address().equals(address) &&
                        repository.findAllMedicalRecords().stream()
                                .anyMatch(mR -> p.firstName().equals(mR.firstName()) &&
                                        p.lastName().equals(mR.lastName())))
                .toList();
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

        if (children.isEmpty()){
            return new ChildrenAlertResponseDTO(List.of());
        }
        List<ChildWithHouseHoldMembersDTO>  childWithHouseHoldMembers = new ArrayList<>();

        for(ChildDTO child : children){
            List<HouseHoldMemberDTO> houseHoldMembers = new ArrayList<>();
            for(Person p : personsAtAddress){
                if(child.lastName().equals(p.lastName())
                        && !child.firstName().equals(p.firstName())){
                    HouseHoldMemberDTO houseHoldMemberDTO = new HouseHoldMemberDTO(
                                                                                    p.lastName(),
                                                                                    p.firstName(),
                                                                                    p.phone(),
                                                                                    p.address(),
                                                                                    p.city());
                    houseHoldMembers.add(houseHoldMemberDTO);
                }
            }
            childWithHouseHoldMembers.add(new ChildWithHouseHoldMembersDTO(child.firstName(),child.lastName(),child.age(), houseHoldMembers));
        }
        return new ChildrenAlertResponseDTO(childWithHouseHoldMembers);
    } //complexity O(n)^2, probably better with a keyMapper we could have O(n).
}
