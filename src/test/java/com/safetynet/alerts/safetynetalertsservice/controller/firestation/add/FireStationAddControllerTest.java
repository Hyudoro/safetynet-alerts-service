package com.safetynet.alerts.safetynetalertsservice.controller.firestation.add;


import com.safetynet.alerts.safetynetalertsservice.controller.FireStationController;

import com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.add.FireStationAddDTO;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateFireStationMappingException;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireStationController.class)
public class FireStationAddControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockitoBean FireStationService service;


    @Test
    void addFireStationShouldReturn201() throws Exception {
        FireStationAddDTO request = new FireStationAddDTO("333 avenue", "2");

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated());
        verify(service).addFireStation(any(FireStation.class));
    }

    @Test
    void addFireStationShouldReturn409IfDuplicates() throws Exception {
        FireStationAddDTO request = new FireStationAddDTO("333 avenue", "2");
        doThrow(new DuplicateFireStationMappingException("333 avenue", "2")).when(service).addFireStation(any(FireStation.class));

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isConflict());
    }

    @Test
    void addFireStationShouldReturn400WhenFireStationDTOAddressIsBlank() throws Exception {
        FireStationAddDTO request = new FireStationAddDTO("","");

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void addFireStationShouldReturn400whenFireStationDTOStationIsBlank() throws Exception {
        FireStationAddDTO request = new FireStationAddDTO("333 avenue","");
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }
}
