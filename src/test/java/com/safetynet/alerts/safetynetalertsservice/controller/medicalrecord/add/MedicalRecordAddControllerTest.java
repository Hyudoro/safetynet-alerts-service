package com.safetynet.alerts.safetynetalertsservice.controller.medicalrecord.add;

import com.safetynet.alerts.safetynetalertsservice.controller.MedicalRecordsController;
import com.safetynet.alerts.safetynetalertsservice.dto.requests.medicalrecord.add.MedicalRecordAddDTO;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicateMedicalRecordMappingException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordsController.class)
public class MedicalRecordAddControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean MedicalRecordService service;

    @Test
    void addMedicalRecord_ShouldReturn201() throws Exception {
        List<String> medications = new ArrayList<>(List.of("aznol:350mg","pharmacol:200mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan","peanut","shellfish"));
        MedicalRecordAddDTO request = new MedicalRecordAddDTO("George",
                "monte",
                "03/06/1984",
                medications,
                allergies);
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated());

    }

    @Test
    void addMedicalRecord_ShouldReturn400_IfParamsNotValid() throws Exception {
        List<String> medications = new ArrayList<>(List.of("  ","pharmacol:200mg"));
        List<String> allergies = new ArrayList<>();
        MedicalRecordAddDTO request = new MedicalRecordAddDTO(
                "",
                "monte",
                "03/06/1984",
                medications,
                allergies);
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
    }

    @Test
    void addMedicalRecord_ShouldReturn409_IfAlreadyExists() throws Exception {
        List<String> medications = new ArrayList<>(List.of("pharmacol:200mg"));
        List<String> allergies = new ArrayList<>(List.of("butter"));
        MedicalRecordAddDTO request = new MedicalRecordAddDTO(
                "Mark",
                "Delandrin",
                "03/06/1984",
                medications,
                allergies);
        BDDMockito.doThrow(DuplicateMedicalRecordMappingException.class).when(service).addMedicalRecord(any(MedicalRecord.class));
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isConflict());
    }

}
