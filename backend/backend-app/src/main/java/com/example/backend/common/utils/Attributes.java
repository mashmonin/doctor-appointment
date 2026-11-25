package com.example.backend.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс с атрибутами для поиска по критериям.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Attributes {
    public static final String ASC_ORDER_KEY = "ASC";
    public static final String DESC_ORDER_KEY = "DESC";
    public static final String PATIENT_NAME = "patientName";
    public static final String DOCTOR_SPECIALIZATION = "doctorSpecialization";
    public static final String CREATION_DATE = "creationDate";
}
