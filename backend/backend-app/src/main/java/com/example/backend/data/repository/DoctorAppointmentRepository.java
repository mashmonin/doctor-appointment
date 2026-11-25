package com.example.backend.data.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.example.backend.data.entity.DoctorAppointment;

/**
 * Репозиторий для работы с {@link DoctorAppointment}
 */
@Repository
public interface DoctorAppointmentRepository extends JpaRepositoryImplementation<DoctorAppointment, Long> {
}
