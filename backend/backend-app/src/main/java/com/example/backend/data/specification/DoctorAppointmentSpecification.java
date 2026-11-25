package com.example.backend.data.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.example.backend.data.entity.DoctorAppointmentShort;
import com.example.backend.model.FilterDto;
import com.example.backend.common.specification.BaseSpecification;

import static com.example.backend.common.specification.utils.SpecificationUtils.*;
import static com.example.backend.common.utils.Attributes.*;
import static com.example.backend.common.utils.StringUtils.normalizeSearchText;

/**
 * Реализация {@link BaseSpecification} для записей к врачу
 */
@Service
public class DoctorAppointmentSpecification implements BaseSpecification<DoctorAppointmentShort, FilterDto> {

    private final Set<String> textPredicateAttributes = Set.of(PATIENT_NAME, DOCTOR_SPECIALIZATION);

    /**
     * Возвращает спецификацию с предикатами
     * @param filter фильтр по инциденту
     * @return спецификации с предикатами
     */
    @Override
    public Specification<DoctorAppointmentShort> getSpecification(FilterDto filter) {
        return (r, q, cb) -> {
            List<Predicate> andPredicates = new ArrayList<>();
            //createPredicates(filter, r, cb, andPredicates);

            createOrPeriodPredicate(cb, r, filter.getCreationDate(), CREATION_DATE, andPredicates);

            List<Predicate> orPredicates = new ArrayList<>();
            var replacedText = normalizeSearchText(filter.getText());
            if (isNotEmpty(replacedText)) {
                createTextPredicates(replacedText, r, cb, orPredicates);
            }

            if (!orPredicates.isEmpty()) {
                andPredicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
            }

            andPredicates.add(createOrPredicate(cb, orPredicates));

            return createAndPredicate(cb, andPredicates);
        };

    }

    private void createTextPredicates(String text, Root<DoctorAppointmentShort> r, CriteriaBuilder cb, List<Predicate> orPredicates) {
        if (isNotEmpty(text)) {
            textPredicateAttributes.forEach(attribute -> orPredicates.add(createLikePredicate(r, cb, text, attribute)));
        }
    }
}
