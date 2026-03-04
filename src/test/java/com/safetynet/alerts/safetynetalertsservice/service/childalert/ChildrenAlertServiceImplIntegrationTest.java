package com.safetynet.alerts.safetynetalertsservice.service.childalert;



import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildWithHouseHoldMembersDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildrenAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.HouseHoldMemberDTO;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.interfaces.ChildrenAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ChildrenAlertServiceImplIntegrationTest {

    @Autowired ChildrenAlertService service;


    @Test
    void shouldReturnChildrenAndTheirHouseHoldMembersByAddressIntegrationTest() {
        ChildrenAlertResponseDTO response = service.getChildrenAndTheirHouseHoldMembersByAddress("1509 Culver St");

        List<ChildWithHouseHoldMembersDTO> childWithHouseHoldMembersDTOList = response.childAndHouseHoldMembers();
        assertThat(childWithHouseHoldMembersDTOList).size().isEqualTo(2);

        List<HouseHoldMemberDTO> hHMChild1 = childWithHouseHoldMembersDTOList.getFirst().houseHoldMembers();
        List<HouseHoldMemberDTO> hHMChild2 = childWithHouseHoldMembersDTOList.getLast().houseHoldMembers();
        assertThat(hHMChild1).size().isEqualTo(4);
        assertThat(hHMChild2).size().isEqualTo(4);
    }

    @Test
    void shouldReturnEmptyResponseIfNoChildrenFoundAtThatAddressTest(){
        ChildrenAlertResponseDTO response = service.getChildrenAndTheirHouseHoldMembersByAddress("any");
        assertThat(response.childAndHouseHoldMembers()).isEmpty();
    }

}
