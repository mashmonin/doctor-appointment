package com.example.backend.data.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name="doctor_appointment")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@SequenceGenerator(name = "doctor_appointment_gen", sequenceName = "doctor_appointment_seq", allocationSize = 1)
public class DoctorAppointmentShort {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_appointment_gen")
    private Long id;

    /**
     * Дата создания
     */
    private LocalDate creationDate;

    /**
     * ФИО пациента
     */
    private String patientName;

    /**
     * Контактный номер пациента
     */
    private String patientPhone;

    /**
     * Контактная почта пациента
     */
    private String patientEmail;

    /**
     * Специализация врача
     */
    private String doctorSpecialization;
}
