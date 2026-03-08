package com.safetynet.alerts.safetynetalertsservice.controller.firestation.update;

import com.safetynet.alerts.safetynetalertsservice.controller.FireStationController;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.firestation.update.FireStationUpdateDTO;

import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldFireStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FireStationController.class)
public class FireStationUpdateControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean FireStationService service;
    @Autowired ObjectMapper objectMapper;

    @Test
    void updateFireStationStationCovering_ShouldReturn204() throws Exception {
        FireStationUpdateDTO request = new FireStationUpdateDTO("200 avenue","2","3");
       mockMvc.perform(patch("/firestation")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(request))).andExpect(status().is(204));

    }

    @Test
    void updateFireStationStationCovering_ShouldReturn409_IfStationNotFound() throws Exception {
        FireStationUpdateDTO request = new FireStationUpdateDTO("200 avenue","999","33");
        doThrow(new OldFireStationNotFoundException("200 avenue", "999")).when(service).updateFireStation(any(FireStation.class),anyInt());
        mockMvc.perform(patch("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isNotFound());
    }

    @Test
    void updateFireStationStationCovering_ShouldReturn409_IfAddressNotFound() throws Exception {
        FireStationUpdateDTO request = new FireStationUpdateDTO("anonymous","2","33");
        doThrow(new OldFireStationNotFoundException("anonymous", "2")).when(service).updateFireStation(any(FireStation.class),anyInt());
        mockMvc.perform(patch("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isNotFound());
    }

    @Test
    void updateFireStationStationCovering_ShouldReturn404_IfNewStationNumberBlank() throws Exception {
        FireStationUpdateDTO request = new FireStationUpdateDTO("200 avenue","2","");
        mockMvc.perform(patch("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }












}
