package com.example.backend.api.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Краткая информация о записе к врачу
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "Краткая информация о записе к врачу")
public class DoctorAppointmentShortDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4939724062250216271L;

    /**
     * Идентификатор записи
     */
    @Schema(description = "Идентификатор записи")
    private String id;

    /**
     * Дата создания
     */
    @Schema(description = "Дата создания")
    private LocalDate creationDate;

    /**
     * ФИО пациента
     */
    @Schema(description = "ФИО пациента")
    private String patientName;

    /**
     * Номер телефона пациента
     */
    @Schema(description = "Номер телефона пациента")
    private String patientPhone;

    /**
     * Почта пациента
     */
    @Schema(description = "Почта пациента")
    private String patientEmail;

    /**
     * Значение справочника специализации врача
     */
    @Schema(description = "Значение справочника специализации врача")
    private String doctorSpecialization;
}

