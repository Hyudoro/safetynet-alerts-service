package com.safetynet.alerts.safetynetalertsservice.controller.firestation.read;

import com.safetynet.alerts.safetynetalertsservice.controller.FireStationController;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.ResidentDTO;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(FireStationController.class)
public class FireStationReadControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FireStationService service;


    @Test
    void getResidentsByStation_ShouldReturn200WithResidents_WhenStationFound() throws Exception {

        ResidentDTO resident = new ResidentDTO("John", "Boyd", "1509 Culver St", "841-874-6512");
        FireStationResponseDTO stubResponse = new FireStationResponseDTO(List.of(resident), 1, 0);
        given(service.getResidentsByStation("3")).willReturn(stubResponse);


        mockMvc.perform(get("/firestation").param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents", hasSize(1)))
                .andExpect(jsonPath("$.residents[0].firstName").value("John"))
                .andExpect(jsonPath("$.residents[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$.adultCount").value(1))
                .andExpect(jsonPath("$.childCount").value(0));
    }


    @Test
    void getResidentsByStation_ShouldReturn200WithEmptyList_WhenNoResidents() throws Exception {

        FireStationResponseDTO response = new FireStationResponseDTO(List.of(), 0, 0);
        given(service.getResidentsByStation("99")).willReturn(response);


        mockMvc.perform(get("/firestation").param("stationNumber", "99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents", hasSize(0)))
                .andExpect(jsonPath("$.adultCount").value(0))
                .andExpect(jsonPath("$.childCount").value(0));
    }


    @Test
    void getResidentsByStation_ShouldReturn400_WhenStationNumberIsBlank() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", ""))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getResidentsByStation_ShouldReturn400_WhenStationNumberIsMissing() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isBadRequest());
    }




}

