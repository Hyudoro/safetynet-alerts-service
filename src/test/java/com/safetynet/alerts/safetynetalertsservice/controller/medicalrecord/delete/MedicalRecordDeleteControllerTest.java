package com.safetynet.alerts.safetynetalertsservice.controller.medicalrecord.delete;

import com.safetynet.alerts.safetynetalertsservice.controller.MedicalRecordsController;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldMedicalRecordNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.service.medicalrecord.interfaces.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordsController.class)
public class MedicalRecordDeleteControllerTest {
    @MockitoBean MedicalRecordService service;
    @Autowired MockMvc mockMvc;


    @Test
    void deleteMedicalRecord_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/medicalRecord/{firestName}/{lastName}","Fred","Fredi"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMedicalRecord_ShouldReturn400_IfConstraintsFail() throws Exception {
        //  %20 = URL-encoded of a space : " "
        char space = (char)32;
        mockMvc.perform(delete("/medicalRecord/{firstName}/{lastName}",space,space))
                .andExpect(status()
                .isBadRequest());
    }

    @Test
    void deleteMedicalRecord_ShouldReturn404_IfNotFound() throws Exception {
        BDDMockito.doThrow(OldMedicalRecordNotFoundException.class).when(service).deleteMedicalRecord("Unknown","Unknown");
        mockMvc.perform(delete("/medicalRecord/{firstName}/{lastName}","Unknown","Unknown")).andExpect(status().isNotFound());
    }

    @Test



}
