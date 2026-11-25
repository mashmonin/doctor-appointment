package com.example.backend.common.utils;

import java.util.Locale;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Утилитный класс для работы со строками
 */
@Slf4j
public class StringUtils {

    public static final char YO_CHAR = 'ё';

    public static final char E_CHAR = 'е';

    public static final char IY_CHAR = 'й';

    public static final char I_CHAR = 'и';

    private static final String LIKE_WILDCARD = "%";

    private StringUtils() {}

    /**
     * Заменяет символы в строке поиска
     * @param searchField строка поиска
     * @return строка поиска с заменными символами
     */
    public static String getReplacedPattern(String searchField) {
        return searchField == null ? null:
                searchField
                        .replace("\\", "\\\\")
                        .replace("'", "\\'")
                        .replace("`", "\\'")
                        .replace("\"", "\\\"");
    }

    /**
     * Преобразует строку в число
     * @param param строка
     * @return число, если строка содержит число, иначе null
     */
    public static Integer safeStringToInt(String param) {
        return Optional.ofNullable(param).filter(org.apache.commons.lang3.StringUtils::isNumeric).map(Integer::valueOf).orElse(null);
    }

    /**
     * Приводит строку к lowercase и исключает пустые символы
     * @param string строка
     * @return признак совпадения
     */
    public static String prepareString(String string) {
        return string.toLowerCase(Locale.ROOT).replaceAll("\\s", "");
    }

    /**
     * Заменяет символы в строке поиска
     * @param string строка поиска
     * @return строка поиска с эаменными символами
     */
    public static String getLetterReplacedPattern(String string) {
        return string.toLowerCase()
                .replace(YO_CHAR, E_CHAR)
                .replace(IY_CHAR, I_CHAR);
    }

    /**
     * Подготавливает строку к запросу типа "like"
     * @param text строка поиска
     * @return подготовленная строка
     */
    public static String prepareToLikeClause(String text) {
        return LIKE_WILDCARD + text + LIKE_WILDCARD;
    }
}
