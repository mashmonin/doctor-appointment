package com.example.backend.common.specification.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.example.backend.common.utils.StringUtils.E_CHAR;
import static com.example.backend.common.utils.StringUtils.YO_CHAR;
import static com.example.backend.common.utils.StringUtils.I_CHAR;
import static com.example.backend.common.utils.StringUtils.IY_CHAR;
import static com.example.backend.common.utils.StringUtils.getLetterReplacedPattern;
import com.example.backend.model.PeriodDto;
import com.example.backend.common.exception.CriteriaFilteringException;

/**
 * Вспомогательный класс для поиска по критериям
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class SpecificationUtils {

    private static final String STRING_REPLACE_METHOD_NAME = "replace";

    /**
     * Создает like-предикат для строкового поля.
     * @param r root
     * @param cb criteriaBuilder
     * @param text строка поиска
     * @param attributeName имя атрибута
     * @return like-предикат
     */
    public static Predicate createLikePredicate(Root<?> r, CriteriaBuilder cb, String text, String attributeName) {
        return cb.like(getReplaceExpression(r, cb, attributeName), "%" + getLetterReplacedPattern(text) + "%");
    }

    private static Expression<String> getReplaceExpression(Expression<String> exp, CriteriaBuilder cb) {
        Expression<String> expression = cb.function(STRING_REPLACE_METHOD_NAME, String.class, cb.lower(exp), cb.literal(IY_CHAR), cb.literal(I_CHAR));
        return cb.function(STRING_REPLACE_METHOD_NAME, String.class, expression, cb.literal(YO_CHAR), cb.literal(E_CHAR));
    }

    private static Expression<String> getReplaceExpression(Root<?> r, CriteriaBuilder cb, String attributeName) {
        Expression<String> expression = cb.function(STRING_REPLACE_METHOD_NAME, String.class, cb.lower(r.get(attributeName)),
                cb.literal(IY_CHAR), cb.literal(I_CHAR));
        return cb.function(STRING_REPLACE_METHOD_NAME, String.class, expression, cb.literal(YO_CHAR), cb.literal(E_CHAR));
    }

    public static Predicate createPeriodPredicate(CriteriaBuilder cb, Root<?> r, PeriodDto periodDto, String attributeName) {
        var start = periodDto.getStart();
        var end = periodDto.getEnd();
        if (start == null || (end != null && start.isAfter(end))) {
            throw new CriteriaFilteringException(String.format("Ошибка фильтрации. Неверно заданы даты для атрибута: %s", attributeName));
        }
        return end != null ? cb.and(cb.greaterThanOrEqualTo(r.get(attributeName), periodDto.getStart()),
                cb.lessThanOrEqualTo(r.get(attributeName), periodDto.getEnd()))
                : cb.equal(r.get(attributeName), periodDto.getStart());
    }

    public static void createOrPeriodPredicate(CriteriaBuilder cb, Root<?> r, PeriodDto periodDto, String attributeName, List<Predicate> predicateList) {
        if (periodDto != null) {
            Predicate predicate = createPeriodPredicate(cb, r, periodDto, attributeName);
            predicateList.add(predicate);
        }
    }

    /**
     * Создает or-предикат из листа предикатов
     * @param cb criteriaBuilder
     * @param predicates лист предикатов
     * @return or-предикат
     */
    public static Predicate createOrPredicate(CriteriaBuilder cb, List<Predicate> predicates) {
        return predicates.isEmpty() ? cb.conjunction() : cb.or(predicates.toArray(new Predicate[0]));
    }

    /**
     * Создает and-предикат из листа предикатов
     * @param cb criteriaBuilder
     * @param predicates лист предикатов
     * @return and-предикат
     */
    public static Predicate createAndPredicate(CriteriaBuilder cb, List<Predicate> predicates) {
        return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
    }

}
