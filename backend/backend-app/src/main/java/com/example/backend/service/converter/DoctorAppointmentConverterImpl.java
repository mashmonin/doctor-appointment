package com.example.backend.service.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.data.entity.DoctorAppointment;
import com.example.backend.model.DoctorAppointmentDto;
import com.example.backend.data.entity.DoctorAppointmentShort;
import com.example.backend.model.DoctorAppointmentShortDto;
import com.example.backend.data.repository.DoctorAppointmentRepository;

@Service
public class DoctorAppointmentConverterImpl implements DoctorAppointmentConverter {

    @Autowired
    private DoctorAppointmentRepository doctorAppointmentRepository;

    @Autowired
    private ModelMapper doctorAppointmentModelMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DoctorAppointmentDto convertToDto(DoctorAppointment entity) {
        return doctorAppointmentModelMapper.map(entity, DoctorAppointmentDto.class);
    }

    @Override
    public DoctorAppointment convertToEntity(DoctorAppointmentDto dto) {
        DoctorAppointment entity;
        if (dto.getId() != null) {
            entity = doctorAppointmentRepository.getReferenceById(Long.valueOf(dto.getId()));
        } else {
            entity = new DoctorAppointment();
        }
        doctorAppointmentModelMapper.map(dto, entity);
        return entity;
    }

    @Override
    public DoctorAppointmentShortDto convertToDtoForGroup(DoctorAppointmentShort doctorAppointment) {
        var shortDto = modelMapper.map(doctorAppointment, DoctorAppointmentShortDto.class);
        return shortDto;
    }
}
