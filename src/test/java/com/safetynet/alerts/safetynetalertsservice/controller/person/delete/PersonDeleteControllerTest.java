package com.safetynet.alerts.safetynetalertsservice.controller.person.delete;

import com.safetynet.alerts.safetynetalertsservice.controller.PersonController;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonDeleteControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean private PersonService service;

    @Test
    void deletePerson_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/person").param("lastName","John").param("firstName","Doe")
                ).andExpect(status().isNoContent());
    }

    @Test
    void deletePerson_ShouldReturn404() throws Exception {
        BDDMockito.doThrow(OldPersonNotFoundException.class).when(service).deletePerson(any());
        mockMvc.perform(delete("/person").param("lastName","Unknown").param("firstName","Unknown")).andExpect(status().isNotFound());
    }

    @Test
    void deletePerson_ShouldReturn400_IfConstraintsInvalid() throws Exception {
        String space = URLEncoder.encode(" ", StandardCharsets.UTF_8);
       mockMvc.perform(delete("/person")
                .param(space)
                .param(space)).andExpect(status().isBadRequest());
    }



}
