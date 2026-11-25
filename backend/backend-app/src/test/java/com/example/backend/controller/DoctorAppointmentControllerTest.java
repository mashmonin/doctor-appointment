package com.example.backend.controller;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.backend.model.DoctorAppointmentDto;
import com.example.backend.model.DoctorAppointmentShortDto;
import com.example.backend.model.FilterDto;
import com.example.backend.service.DoctorAppointmentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DoctorAppointmentControllerTest {

    @Mock private DoctorAppointmentService service;
    @InjectMocks private DoctorAppointmentController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void save_returnsId() throws Exception {
        var dto = new DoctorAppointmentDto()
                .setPatientName("Иванов Иван Иванович")
                .setPatientPhone("+7 (999) 123-45-67")
                .setPatientEmail("ivanov@example.com")
                .setDoctorSpecialization("Терапевт");

        when(service.save(any())).thenReturn(1L);

        mockMvc.perform(post("/api/doctor-appointment/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getList_returnsPage() throws Exception {
        var shortDto = new DoctorAppointmentShortDto()
                .setPatientName("Иванов Иван Иванович")
                .setDoctorSpecialization("Терапевт");
        var page = new PageImpl<>(List.of(shortDto), PageRequest.of(0, 10), 1);

        when(service.getPageOfDoctorAppointment(any(), any())).thenReturn(page);

        mockMvc.perform(post("/api/doctor-appointment/list?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new FilterDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].patientName").value("Иванов Иван Иванович"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getList_withTextFilter_passesFilterToService() throws Exception {
        var filter = new FilterDto(null, "Терапевт");
        when(service.getPageOfDoctorAppointment(any(), any())).thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        mockMvc.perform(post("/api/doctor-appointment/list?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk());

        verify(service).getPageOfDoctorAppointment(any(), argThat(f -> "Терапевт".equals(f.getText())));
    }

    @Test
    void getById_returnsDto() throws Exception {
        var dto = new DoctorAppointmentDto()
                .setId("5")
                .setPatientName("Петров Пётр")
                .setCreationDate(LocalDate.of(2026, 1, 15));

        when(service.getById(5L)).thenReturn(dto);

        mockMvc.perform(get("/api/doctor-appointment/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.patientName").value("Петров Пётр"));
    }

    @Test
    void getById_returns404_whenNotFound() throws Exception {
        when(service.getById(99L)).thenThrow(new EntityNotFoundException("DoctorAppointment with id 99 not found"));

        mockMvc.perform(get("/api/doctor-appointment/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("DoctorAppointment with id 99 not found"));
    }

    @Test
    void update_returns200() throws Exception {
        var dto = new DoctorAppointmentDto()
                .setId("3")
                .setPatientName("Сидоров Сидор")
                .setDoctorSpecialization("Хирург");

        doNothing().when(service).update(any());

        mockMvc.perform(put("/api/doctor-appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
