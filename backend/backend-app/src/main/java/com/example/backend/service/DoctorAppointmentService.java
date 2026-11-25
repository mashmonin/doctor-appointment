package com.example.backend.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.data.entity.DoctorAppointment;
import com.example.backend.model.DoctorAppointmentDto;
import com.example.backend.model.DoctorAppointmentShortDto;
import com.example.backend.model.FilterDto;

public interface DoctorAppointmentService {
    /**
     * Создает новую запись к врачу
     * @param doctorAppointmentDto -
     */
    Long save(DoctorAppointmentDto doctorAppointmentDto) throws IOException;

    /**
     * Обновляет запись ко врачу с заданным ID,
     * в соответствии с переданной записью ко врачу
     * @param doctorAppointmentDto - запись ко врачу в соответствии с которым нужно обновить данные
     * @return - true если данные были обновлены, иначе false
     */
    void update(DoctorAppointmentDto doctorAppointmentDto) throws IOException;

    /**
     * Возвращает страницу списка записей к врачу
     * @param pageable пагинация
     * @param filterDto фильтр поиска
     * @return страница списка записей к врачу
     */
    Page<DoctorAppointmentShortDto> getPageOfDoctorAppointment(Pageable pageable, FilterDto filterDto);

    /**
     * Возвращает запись ко врачу по её ID
     * @param id - ID записи
     * @return - объект записи ко врачу с заданным ID
     */
    DoctorAppointment getEntityById(Long id);

    /**
     * Возвращает запись ко врачу по её ID
     * @param id - ID записи
     * @return - объект записи ко врачу с заданным ID
     */
    DoctorAppointmentDto getById(Long id);

}
