package com.mehdi.optimizedbasket.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Nested
    @DisplayName("Tests for isEmptyCollection")
    class IsEmptyCollectionTests {

        @Test
        @DisplayName("Null collection, except true")
        void nullCollection() {
            assertTrue(Utils.isEmptyCollection(null));
        }

        @Test
        @DisplayName("Empty collection, except true")
        void emptyCollection() {
            assertTrue(Utils.isEmptyCollection(Collections.emptyList()));
        }

        @Test
        @DisplayName("Not empty collection, except false")
        void notEmptyCollection() {
            List<String> list = new ArrayList<>();
            list.add("test");

            assertFalse(Utils.isEmptyCollection(list));
        }
    }

    @Nested
    @DisplayName("Tests for isNumberEquals")
    class IsNumberEqualsTests {

        @Test
        @DisplayName("Null and not null values, except false")
        void nullAndNotNullValues() {
            assertFalse(Utils.isNumberEquals(null, BigDecimal.TEN));
            assertFalse(Utils.isNumberEquals(BigDecimal.TEN, null));
        }

        @Test
        @DisplayName("Null values, except true")
        void nullValues() {
            assertTrue(Utils.isNumberEquals(null, null));
        }

        @Test
        @DisplayName("Not equal values, except false")
        void notEqualValues() {
            assertFalse(Utils.isNumberEquals(BigDecimal.valueOf(1.0001), BigDecimal.valueOf(1.0002)));
        }

        @Test
        @DisplayName("Equal values, except true")
        void equalValues() {
            assertTrue(Utils.isNumberEquals(BigDecimal.valueOf(1.0001), BigDecimal.valueOf(1.0001)));
        }
    }
}