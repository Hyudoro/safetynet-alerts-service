package com.safetynet.alerts.safetynetalertsservice.controller.medicalrecord.update;


import com.safetynet.alerts.safetynetalertsservice.controller.MedicalRecordsController;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordsController.class)
public class MedicalRecordUpdateAllergiesControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean MedicalRecordService service;
    @Autowired ObjectMapper objectMapper;
    @Test
    void updateMedicalRecordAllergies_ShouldReturn204() throws Exception {
        List<String> allergies = new ArrayList<>(List.of("butter","peach"));
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}/allergies","Jean","mark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(allergies)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateMedicalRecordAllergies_ShouldReturn404() throws Exception {
        BDDMockito.doThrow(OldMedicalRecordNotFoundException.class)
                .when(service)
                .updateAllergyMedicalRecord("Jean","mark",new ArrayList<>(List.of("butter","peach")));
        List<String> allergies = new ArrayList<>(List.of("butter","peach"));
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}/allergies","Jean","mark")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(allergies)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMedicalRecordAllergies_ShouldReturn400_IfConstraintsInvalid() throws Exception {
        List<String> allergies = new ArrayList<>(List.of(""," "));
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}/allergies","Jean","Mark")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(allergies)))
                .andExpect(status().isBadRequest());
    }



}
