package com.example.backend.common.exception;

import java.io.Serial;

/**
 * Ошибка фильтрации по критериям
 */
public class CriteriaFilteringException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5226495048461644168L;

    /**
     * Конструктор исключения при фильтрации по критериям
     * @param message сообщение об ошибке
     */
    public CriteriaFilteringException(String message) {
        super(message);
    }
}
