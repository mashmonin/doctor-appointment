package com.example.backend.api.dto;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Фильтр информации о записях к врачу
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "Фильтр информации о записях к врачу")
public class DoctorAppointmentFilterDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 183019350458423710L;

    /**
     * Период времени создания записи к врачу
     */
    @Schema(description = "Период времени создания записи к врачу")
    private PeriodDto creationDate;

    /**
     * Строка поиска
     */
    @Schema(description = "Строка поиска")
    private String text;
}

