package com.example.backend.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

import com.example.backend.model.DoctorAppointmentDto;
import com.example.backend.model.DoctorAppointmentShortDto;
import com.example.backend.model.FilterDto;
import com.example.backend.service.DoctorAppointmentService;

@Slf4j
@RestController
@RequestMapping("/api/doctor-appointment")
@Tag(name = "DoctorAppointmentController", description = "Контроллер для обработки запросов о записях к врачу")
public class DoctorAppointmentController {

    // Объявляем и инициализируем логгер
    private final Logger logger = LoggerFactory.getLogger(DoctorAppointmentController.class);

    @Autowired
    private DoctorAppointmentService doctorAppointmentService;

    /**
     * Сохраняет информацию о новой записи к врачу
     * @param dto информация о новой записи
     * @return пара ключ-значение id новой записи
     */
    @PostMapping("/create")
    @Operation(summary = "Сохраняет запрос на создание записи к врачу")
    public Map<String, Long> save(@RequestBody DoctorAppointmentDto dto) throws IOException {
        long id = doctorAppointmentService.save(dto);
        return Collections.singletonMap("id", id);
    }

    /**
     * Возвращает страницу записей к врачу с учётом фильтрации и пагинации
     * @param pageable параметры пагинации
     * @param filterDto объект фильтра
     * @return страница с краткой информацией о записях к врачу
     */
    @PostMapping("/list")
    @Operation(summary = "Получает список записей к врачу с учетом фильтрации и пагинации")
    public Page<DoctorAppointmentShortDto> getPageOfDoctorAppointment(
            @Parameter(description = "Параметры пагинации") Pageable pageable,
            @Parameter(description = "Объект фильтра") @RequestBody FilterDto filterDto) {
        logger.info("Received FilterDto: {}", filterDto.toString());
        return doctorAppointmentService.getPageOfDoctorAppointment(pageable, filterDto);
    }

    /**
     * Обновляет информацию о записи к врачу
     * @param dto информация об обновляемой записи
     */
    @PutMapping
    @Operation(summary = "Обновляет информацию о записи к врачу")
    public void update(@RequestBody DoctorAppointmentDto dto) throws IOException {
        doctorAppointmentService.update(dto);
    }

    /**
     * Запрашивает информацию о записи к врачу
     * @param id идентификатор записи к врачу
     * @return информация о записи к врачу
     */
    @GetMapping("/{id}")
    @Operation(summary = "Запрашивает информацию о записи к врачу")
    public DoctorAppointmentDto getById(@PathVariable("id") Long id) {
        return doctorAppointmentService.getById(id);
    }
}