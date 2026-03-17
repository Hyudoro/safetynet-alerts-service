package com.safetynet.alerts.safetynetalertsservice.controller.childalert;

import com.safetynet.alerts.safetynetalertsservice.controller.ChildAlertController;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildWithHouseHoldMembersDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.childalert.ChildrenAlertResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.service.childalert.interfaces.ChildrenAlertService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChildAlertController.class)
public class ChildAlertControllerTest {
    @MockitoBean private ChildrenAlertService service;
    @Autowired MockMvc mockMvc;

    @Test
    void getChildrenByAddress_ShouldReturn204() throws Exception {
        ChildWithHouseHoldMembersDTO childWithHouseHoldMembersDTO = new ChildWithHouseHoldMembersDTO(
                "Larry",
                "Dupont",
                8,
                any());
        ChildrenAlertResponseDTO response = new ChildrenAlertResponseDTO(List.of(childWithHouseHoldMembersDTO));
        BDDMockito.given(service.getChildrenAndTheirHouseHoldMembersByAddress("3 rue martigo")).willReturn(response);
        mockMvc.perform(get("/childAlert").param("address", "3 rue martigo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.childAndHouseHoldMembers", hasSize(1)))
                .andExpect(jsonPath("$.childAndHouseHoldMembers[0].firstName").value("Larry"))
                .andExpect(jsonPath("$.childAndHouseHoldMembers[0].lastName").value("Dupont"))
                .andExpect(jsonPath("$.childAndHouseHoldMembers[0].age").value(8))
                .andExpect(jsonPath("$.childAndHouseHoldMembers[0].houseHoldMembers").doesNotExist());
    }
    @Test
    void getChildrenByAddress_ShouldReturnEmptyList_IfAddressNotFound() throws Exception {
        ChildrenAlertResponseDTO response = new ChildrenAlertResponseDTO(List.of());
        BDDMockito.given(service.getChildrenAndTheirHouseHoldMembersByAddress("3 rue martigo")).willReturn(response);
        
    }

}
