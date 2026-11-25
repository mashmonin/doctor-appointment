package com.example.backend.api.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Информация о периоде фильтрации
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(description="Информация о периоде фильтрации")
public class PeriodDto implements Serializable{

    @Serial
    private static final long serialVersionUID = -3926915209826885117L;

    /**
     * Начало периода
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Начало периода")
    private LocalDate start;

    /**
     * Конец периода
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Конец периода")
    private LocalDate end;
}
