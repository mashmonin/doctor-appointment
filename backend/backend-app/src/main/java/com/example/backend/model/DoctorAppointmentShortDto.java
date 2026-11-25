package com.example.backend.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DoctorAppointmentShortDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 4939724062250216271L;
    private String id;
    private LocalDate creationDate;
    private String patientName;
    private String patientPhone;
    private String patientEmail;
    private String doctorSpecialization;
}
