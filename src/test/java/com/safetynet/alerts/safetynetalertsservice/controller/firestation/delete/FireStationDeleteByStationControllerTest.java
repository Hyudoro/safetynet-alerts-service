package com.safetynet.alerts.safetynetalertsservice.controller.firestation.delete;

import com.safetynet.alerts.safetynetalertsservice.controller.FireStationController;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireStationController.class)
public class FireStationDeleteByStationControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean FireStationService service;


    @Test
    void DeleteFireStationByStation_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/firestation")
               .param("stationNumber","5"))
               .andExpect(status().isNoContent());
    }

    @Test
    void DeleteFireStationByStation_ShouldReturn404_IfStationNumberIsNotFound()throws Exception
    {
        doThrow(new MappingWithStationNotFoundException("99")).when(service).deleteMappingsByStation(anyString());
        mockMvc.perform(delete("/firestation").param("stationNumber", "99")).andExpect(status().isNotFound());
    }

    @Test
    void DeleteFireStationByStation_ShouldReturn400_IfStationNumberIsBlank()throws Exception
    {
        mockMvc.perform(delete("/firestation").param("stationNumber","")).andExpect(status().isBadRequest());
    }





}
