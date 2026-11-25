package com.example.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.example.backend.data.entity.DoctorAppointment;
import com.example.backend.data.entity.DoctorAppointmentShort;
import com.example.backend.data.repository.DoctorAppointmentRepository;
import com.example.backend.data.repository.DoctorAppointmentShortRepository;
import com.example.backend.data.specification.DoctorAppointmentSpecification;
import com.example.backend.model.DoctorAppointmentDto;
import com.example.backend.model.DoctorAppointmentShortDto;
import com.example.backend.model.FilterDto;
import com.example.backend.service.converter.DoctorAppointmentConverter;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorAppointmentServiceImplTest {

    @Mock private DoctorAppointmentConverter converter;
    @Mock private DoctorAppointmentRepository repository;
    @Mock private DoctorAppointmentShortRepository shortRepository;
    @Mock private DoctorAppointmentSpecification specification;

    @InjectMocks private DoctorAppointmentServiceImpl service;

    @Test
    void save_setsCreationDateToTodayAndReturnsId() throws Exception {
        var dto = new DoctorAppointmentDto();
        var entity = new DoctorAppointment().setId(42L);
        when(converter.convertToEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        Long id = service.save(dto);

        assertThat(id).isEqualTo(42L);
        assertThat(dto.getCreationDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void getById_returnsDto_whenEntityExists() {
        var entity = new DoctorAppointment().setId(1L);
        var dto = new DoctorAppointmentDto().setId("1");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(converter.convertToDto(entity)).thenReturn(dto);

        var result = service.getById(1L);

        assertThat(result.getId()).isEqualTo("1");
    }

    @Test
    void getById_throwsEntityNotFoundException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void update_savesConvertedEntity() {
        var dto = new DoctorAppointmentDto();
        var entity = new DoctorAppointment();
        when(converter.convertToEntity(dto)).thenReturn(entity);

        service.update(dto);

        verify(repository).save(entity);
    }

    @Test
    void getPageOfDoctorAppointment_returnsMappedPage() {
        var filter = new FilterDto();
        var pageable = PageRequest.of(0, 10);
        var shortEntity = new DoctorAppointmentShort();
        var shortDto = new DoctorAppointmentShortDto().setPatientName("Иванов Иван");
        Page<DoctorAppointmentShort> entityPage = new PageImpl<>(List.of(shortEntity));

        when(specification.getSpecification(filter)).thenReturn(mock(Specification.class));
        when(shortRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(entityPage);
        when(converter.convertToDtoForGroup(shortEntity)).thenReturn(shortDto);

        Page<DoctorAppointmentShortDto> result = service.getPageOfDoctorAppointment(pageable, filter);

        assertThat(result.getContent()).containsExactly(shortDto);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getPageOfDoctorAppointment_returnsEmptyPage_whenNoResults() {
        var filter = new FilterDto();
        var pageable = PageRequest.of(0, 10);

        when(specification.getSpecification(filter)).thenReturn(mock(Specification.class));
        when(shortRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(Page.empty());

        Page<DoctorAppointmentShortDto> result = service.getPageOfDoctorAppointment(pageable, filter);

        assertThat(result).isEmpty();
    }
}
