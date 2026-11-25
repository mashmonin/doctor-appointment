package com.example.backend.service;

import java.io.IOException;

import java.time.LocalDate;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.data.entity.DoctorAppointment;
import com.example.backend.model.DoctorAppointmentDto;
import com.example.backend.model.DoctorAppointmentShortDto;
import com.example.backend.model.FilterDto;
import com.example.backend.service.converter.DoctorAppointmentConverter;
import com.example.backend.data.repository.DoctorAppointmentRepository;
import com.example.backend.data.repository.DoctorAppointmentShortRepository;
import com.example.backend.data.specification.DoctorAppointmentSpecification;

@Service
public class DoctorAppointmentServiceImpl implements DoctorAppointmentService {

    @Autowired
    private DoctorAppointmentConverter doctorAppointmentConverter;

    @Autowired
    private DoctorAppointmentRepository doctorAppointmentRepository;

    @Autowired
    private DoctorAppointmentSpecification doctorAppointmentSpecification;

    @Autowired
    private DoctorAppointmentShortRepository doctorAppointmentShortRepository;

    /**
     * Сохраняет информацию о новой записи к врачу
     * @param dto информация о новой записи
     * @return id новой записи
     * @throws IOException в случае ошибки
     */
    @Override
    public Long save(DoctorAppointmentDto dto) throws IOException {
        dto.setCreationDate(LocalDate.now());
        return doctorAppointmentRepository.save(doctorAppointmentConverter.convertToEntity(dto)).getId();
    }

    @Override
    public Page<DoctorAppointmentShortDto> getPageOfDoctorAppointment(Pageable pageable, FilterDto filterDto) {
        return doctorAppointmentShortRepository.findAll(doctorAppointmentSpecification.getSpecification(filterDto), pageable).map(doctorAppointmentConverter::convertToDtoForGroup);
    }

    /**
     * Обновляет информацию о записи к врачу
     * @param dto - запись ко врачу в соответствии с которым нужно обновить данные
     */
    @Override
    public void update(DoctorAppointmentDto dto) {
        doctorAppointmentRepository.save(doctorAppointmentConverter.convertToEntity(dto));
    }

    @Override
    public DoctorAppointment getEntityById(Long id) {
        return doctorAppointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("DoctorAppointment with id %s not found", id)));
    }

    @Override
    public DoctorAppointmentDto getById(Long id) {
        return doctorAppointmentConverter.convertToDto(getEntityById(id));
    }

}
