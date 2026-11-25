package com.example.backend.api;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.api.dto.DoctorAppointmentDto;
import com.example.backend.api.dto.DoctorAppointmentFilterDto;
import com.example.backend.api.dto.DoctorAppointmentShortDto;

/**
 * Операции по сохранению информации о записях к врачу
 */
@RequestMapping("/api/doctor-appointment")
public interface DoctorAppointmentOperations {

    /**
     * Сохраняет информацию о новой записи к врачу
     *
     * @param dto информация о новой записи
     *
     * @return пара ключ-значение id новой записи
     */
    @PostMapping(value = "/create")
    Map<String, Long> save(@RequestBody DoctorAppointmentDto dto) throws IOException;


    /**
     * Возвращает страницу списка записей к врачу
     *
     * @param pageable пагинация
     * @param filterDto фильтр поиска
     *
     * @return страница списка записей к врачу
     */
    @PostMapping("/list")
    Page<DoctorAppointmentShortDto> getPageOfDoctorAppointment(Pageable pageable, @RequestBody DoctorAppointmentFilterDto filterDto);

}
