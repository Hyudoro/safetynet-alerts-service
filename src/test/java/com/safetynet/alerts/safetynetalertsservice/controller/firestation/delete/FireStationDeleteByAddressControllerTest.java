package com.safetynet.alerts.safetynetalertsservice.controller.firestation.delete;

import com.safetynet.alerts.safetynetalertsservice.controller.FireStationController;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.FireStationService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FireStationController.class)
public class FireStationDeleteByAddressControllerTest {

    @MockitoBean FireStationService service;
    @Autowired MockMvc mockMvc;

    @Test
    void DeleteFireStationByAddress_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/firestation").param("address","300 rue Fires")).andExpect(status().isNoContent());
    }

    @Test
    void DeleteFireStationByAddress_ShouldReturn400IfAddressBlank()throws Exception{
        mockMvc.perform(delete("/fireStation").param("address","")).andExpect(status().isNotFound());
    }

    @Test
    void DeleteFireStationByAddress_ShouldReturn404IfAddressNotFound()throws Exception{
        BDDMockito.doThrow(MappingWithAddressNotFoundException.class).when(service).deleteMappingsByAddress("unknown address");
        mockMvc.perform(delete("/firestation").param("address","unknown address")).andExpect(status().isNotFound());

    }

}
