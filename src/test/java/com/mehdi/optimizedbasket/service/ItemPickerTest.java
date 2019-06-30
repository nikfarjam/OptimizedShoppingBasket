package com.mehdi.optimizedbasket.service;

import co.unruly.matchers.OptionalMatchers;
import com.mehdi.optimizedbasket.builder.ItemBuilder;
import com.mehdi.optimizedbasket.factory.ClassFactory;
import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.ItemRange;
import com.mehdi.optimizedbasket.model.ItemValueRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mehdi.optimizedbasket.factory.ClassFactory.ITEM_PICKER_GREEDY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemPickerTest {

    private ItemPicker picker;

    @BeforeEach
    void setUp() {
        picker = ClassFactory.getInstance().getItemPicker(ITEM_PICKER_GREEDY);
    }

    @Nested
    @DisplayName("Tests empty values such as null or empty list")
    class EmptyInputTest {

        @Test
        @DisplayName("When items is null result must be empty")
        void nullCategories() {
            Optional<Basket> result = picker.selectItems(null, BigDecimal.TEN);
            assertThat(result, OptionalMatchers.empty());
        }

        @Test
        @DisplayName("When items are empty")
        void noItems() {
            Optional<Basket> result = picker.selectItems(new ArrayList<>(), BigDecimal.TEN);
            assertThat("result must be empty", result, OptionalMatchers.empty());
        }

        @Test
        @DisplayName("When price limit is null")
        void nullLimitPrice() {
            ItemRange TEST_RANGE = new ItemValueRange().setMinPrice(BigDecimal.ONE)
                    .setMaxPrice(BigDecimal.TEN)
                    .setMinShoppingCost(BigDecimal.valueOf(2))
                    .setMaxShoppingCost(BigDecimal.valueOf(5))
                    .setMinRate(1)
                    .setMaxRate(5);
            ItemBuilder builder = ClassFactory.getInstance().createItemBuilder(TEST_RANGE);
            List<Category> categories = builder.getCategoriesAndItems(1, 2);

            Optional<Basket> result = picker.selectItems(categories.get(0).getItems(), null);
            assertThat("result must be empty", result, OptionalMatchers.empty());
        }

    }


}