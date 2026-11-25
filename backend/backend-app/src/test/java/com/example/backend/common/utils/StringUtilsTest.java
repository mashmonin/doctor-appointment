package com.example.backend.common.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    void escapeLikeWildcards_leavesOrdinaryTextUntouched() {
        assertThat(StringUtils.escapeLikeWildcards("михаил 123")).isEqualTo("михаил 123");
    }

    @Test
    void escapeLikeWildcards_escapesPercentAndUnderscore() {
        assertThat(StringUtils.escapeLikeWildcards("50%")).isEqualTo("50\\%");
        assertThat(StringUtils.escapeLikeWildcards("a_b")).isEqualTo("a\\_b");
    }

    @Test
    void escapeLikeWildcards_escapesBackslashFirst() {
        assertThat(StringUtils.escapeLikeWildcards("a\\b")).isEqualTo("a\\\\b");
        assertThat(StringUtils.escapeLikeWildcards("100%_\\")).isEqualTo("100\\%\\_\\\\");
    }

    @Test
    void escapeLikeWildcards_doesNotTouchQuotes() {
        assertThat(StringUtils.escapeLikeWildcards("O'Brien \"x\" `y`")).isEqualTo("O'Brien \"x\" `y`");
    }

    @Test
    void normalizeSearchText_stripsSurroundingWhitespace() {
        assertThat(StringUtils.normalizeSearchText("  михаил ")).isEqualTo("михаил");
        assertThat(StringUtils.normalizeSearchText("   ")).isEmpty();
    }

    @Test
    void normalizeSearchText_keepsNull() {
        assertThat(StringUtils.normalizeSearchText(null)).isNull();
    }
}
