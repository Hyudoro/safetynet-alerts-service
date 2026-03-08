package com.safetynet.alerts.safetynetalertsservice.controller.firestation.delete;

import com.safetynet.alerts.safetynetalertsservice.controller.FireStationController;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationAndAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.FireStationService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireStationController.class)
public class FireStationDeleteByAddressAndStationControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean FireStationService service;

    @Test
    void DeleteFireStationByAddressAndStation_ShouldReturn204()throws Exception {
        mockMvc.perform(delete("/firestation")
                .param("address","300 Marie Curie")
                .param("stationNumber","2")).andExpect(status().isNoContent());
    }

    @Test
    void DeleteFireStationByAddressAndStation_ShouldReturn404() throws  Exception {
        BDDMockito.doThrow(MappingWithStationAndAddressNotFoundException.class).when(service).deleteMapping(any(FireStation.class));
        mockMvc.perform(delete("/firestation")
                .param("address","300 lila avenue")
                .param("stationNumber","100")).andExpect(status().isNotFound());

    }


    @Test
    void DeleteFireStationByAddressAndStation_ShouldReturn400() throws  Exception {
        mockMvc.perform(delete("/firestation").param("address","").param("stationNumber","")).andExpect(status().isBadRequest());
    }
}
