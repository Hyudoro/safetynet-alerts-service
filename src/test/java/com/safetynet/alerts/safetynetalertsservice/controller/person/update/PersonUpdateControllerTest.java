package com.safetynet.alerts.safetynetalertsservice.controller.person.update;

import com.safetynet.alerts.safetynetalertsservice.controller.PersonController;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.person.PersonUpdateDTO;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonUpdateControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    private PersonService service;

    @Test
    void updatePerson_ShouldReturn204() throws Exception {
        PersonUpdateDTO request = new PersonUpdateDTO(
                "742 Evergreen Terrace",
                "Springfield",
                "49007",
                "555-123-4567",
                "homer.simpson@email.com"
        );
        BDDMockito.doNothing().when(service).updatePerson(anyString(), anyString(), any());
        mockMvc.perform(patch("/person/{lastName}/{firstName}", "Doe", "John")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isNoContent());
    }

    @Test
    void updatePerson_ShouldReturn404() throws Exception {
        PersonUpdateDTO request = new PersonUpdateDTO(
                "742 Evergreen Terrace",
                "Springfield",
                "49007",
                "555-123-4567",
                "homer.simpson@email.com"
        );
        BDDMockito.doThrow(OldPersonNotFoundException.class).when(service).updatePerson(anyString(), anyString(), any());
        mockMvc.perform(patch("/person/{lastName}/{firstName}", "Doe", "John")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePerson_ShouldReturn400() throws Exception {
        PersonUpdateDTO request = new PersonUpdateDTO(
                "742 Evergreen Terrace",
                "",
                "    ",
                " ",
                "");
        BDDMockito.doNothing().when(service).updatePerson(anyString(), anyString(), any());
        mockMvc.perform(patch("/person/{lastName}/{firstName}", "Doe","John")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }


}