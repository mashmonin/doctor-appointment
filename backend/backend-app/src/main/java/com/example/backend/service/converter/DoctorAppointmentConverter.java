package com.example.backend.service.converter;

import com.example.backend.data.entity.DoctorAppointment;
import com.example.backend.model.DoctorAppointmentDto;
import com.example.backend.data.entity.DoctorAppointmentShort;
import com.example.backend.model.DoctorAppointmentShortDto;

public interface DoctorAppointmentConverter {
    DoctorAppointmentDto convertToDto(DoctorAppointment entity);
    DoctorAppointment convertToEntity(DoctorAppointmentDto dto);
    DoctorAppointmentShortDto convertToDtoForGroup(DoctorAppointmentShort doctorAppointment);
}
