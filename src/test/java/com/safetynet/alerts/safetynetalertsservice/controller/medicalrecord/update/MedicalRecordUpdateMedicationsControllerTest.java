package com.safetynet.alerts.safetynetalertsservice.controller.medicalrecord.update;

import com.safetynet.alerts.safetynetalertsservice.controller.MedicalRecordsController;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
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
public class MedicalRecordUpdateMedicationsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    MedicalRecordService service;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    void updateMedicalRecordMedications_ShouldReturn204() throws Exception {
        List<String> medications = new ArrayList<>(List.of("paracetamol:500mg","insulin:200mg"));
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}/medications","Jean","mark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medications)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateMedicalRecordMedications_ShouldReturn404() throws Exception {
        BDDMockito.doThrow(OldMedicalRecordNotFoundException.class)
                .when(service)
                .updateMedicationMedicalRecord(new MedicalRecord.Id("Jean","mark"),new ArrayList<>(List.of("paracetamol:500mg","insulin:200mg")));
        List<String> medications = new ArrayList<>(List.of("paracetamol:500mg","insulin:200mg"));
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}/medications","Jean","mark")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(medications)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMedicalRecordAllergies_ShouldReturn400_IfConstraintsInvalid() throws Exception {
        List<String> medications = new ArrayList<>(List.of("", " "));
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}/medications", "Jean", "Mark")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(medications)))
                .andExpect(status().isBadRequest());
    }
}
