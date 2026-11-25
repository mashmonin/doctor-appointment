package com.example.backend.common.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * Сервис создания спецификации для осуществления поиска по критериям
 * @param <E> Сущность
 * @param <F> Фильтр
 */
public interface BaseSpecification<E, F> {
    /**
     * Сервис создания спецификации для осуществления поиска по критериям
     * @param filter фильтр
     * @return спецификация
     */
    Specification<E> getSpecification(F filter);
}
