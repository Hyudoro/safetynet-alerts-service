package com.safetynet.alerts.safetynetalertsservice.controller.person.add;

import com.safetynet.alerts.safetynetalertsservice.controller.PersonController;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.person.PersonAddDTO;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicatePersonMappingException;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
public class PersonAddControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    PersonService personService;


    @Test
    void addPerson_ShouldReturn204() throws Exception {
        PersonAddDTO request = new PersonAddDTO(
                "John",
                "Doe",
                "150 Main Street",
                "New York",
                "10001",
                "123-456-7890",
                "john.doe@email.com"
        );
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated());
    }

    @Test
    void addPerson_ShouldReturn400_IfInvalidConstraints() throws Exception {
        PersonAddDTO request = new PersonAddDTO(
                "",
                "Doe",
                "",
                "New York",
                "10001",
                "",
                " "
        );
        //not forget if already exist
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
    }

    @Test
    void addPerson_ShouldReturn409_IfAlreadyExists() throws Exception {
        PersonAddDTO person = new PersonAddDTO(
                "John",
                "Doe",
                "150 Main Street",
                "New York",
                "10001",
                "123-456-7890",
                "john.doe@email.com"
        );
        BDDMockito.doThrow(DuplicatePersonMappingException.class).when(personService).addPerson(any());
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person))).andExpect(status().isConflict());
    }
}