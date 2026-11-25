package com.example.backend.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 183019350458423710L;

    private PeriodDto creationDate;

    private String text;
}
