--liquibase formatted sql

--changeSet author: mashmonin

CREATE TABLE public.doctor_appointment (
    id bigserial NOT NULL,
    patient_name varchar(255) NULL,
    patient_phone varchar(255) NULL,
    patient_email varchar(255) NULL,
    doctor_specialization varchar(255) NULL,
    creation_date date NULL,
    CONSTRAINT doctor_appointment_pkey PRIMARY KEY (id)
);