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

    public static final char LIKE_ESCAPE_CHAR = '\\';

    private static final String LIKE_WILDCARD = "%";

    private StringUtils() {}

    /**
     * Подготавливает введённую пользователем строку поиска: обрезает пробелы по краям
     * @param searchField строка поиска
     * @return строка без пробелов по краям или null
     */
    public static String normalizeSearchText(String searchField) {
        return searchField == null ? null : searchField.strip();
    }

    /**
     * Экранирует специальные символы оператора like ("\\", "%" и "_"),
     * чтобы они искались как обычные символы. Использовать вместе с escape-символом {@link #LIKE_ESCAPE_CHAR}
     * @param text строка поиска
     * @return строка с экранированными символами
     */
    public static String escapeLikeWildcards(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
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
