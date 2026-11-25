package com.example.backend.data.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.example.backend.data.entity.DoctorAppointmentShort;

/**
 * Репозиторий для работы с {@link DoctorAppointmentShort}
 */
@Repository
public interface DoctorAppointmentShortRepository extends JpaRepositoryImplementation<DoctorAppointmentShort, Long>, JpaSpecificationExecutor<DoctorAppointmentShort> {
}
