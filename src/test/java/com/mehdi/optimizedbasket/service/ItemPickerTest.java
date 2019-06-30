package com.mehdi.optimizedbasket.service;

import co.unruly.matchers.OptionalMatchers;
import com.mehdi.optimizedbasket.builder.ItemBuilder;
import com.mehdi.optimizedbasket.factory.ClassFactory;
import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.model.ItemRange;
import com.mehdi.optimizedbasket.model.ItemValueRange;
import com.mehdi.optimizedbasket.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mehdi.optimizedbasket.factory.ClassFactory.ITEM_PICKER_GREEDY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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

    @Nested
    @DisplayName("Test generated items and categories")
    class GeneratedItemsAndCategoriesTest {

        @Test
        @DisplayName("When all items are more expensive than price limit, so basket is empty")
        void expensiveItems() {
            Item item1 = new Item(1, "test1", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 1, 10);
            Item item2 = new Item(2, "test2", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 2, 10);
            Item item3 = new Item(3, "test3", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 3, 10);
            Item item4 = new Item(4, "test4", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 4, 10);
            Item item5 = new Item(5, "test5", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 5, 10);
            List<Item> items = new ArrayList<>();
            items.add(item1);
            items.add(item2);
            items.add(item3);
            items.add(item4);
            items.add(item5);

            Optional<Basket> result = picker.selectItems(items, BigDecimal.ONE);
            assertTrue(result.isPresent(), "Result must have value");
            Basket basket = result.get();
            assertThat("No item is picked, so sum rating is zero", basket.getSumRating(), is(0));
            assertThat("No item is picked, so total cost is zero", basket.getTotalCost(), is(BigDecimal.ZERO));
            assertTrue(basket.getSelectedItems().isEmpty(), "No item is picked, so selected items is empty");
        }

        @Test
        @DisplayName("When all items have rating of ONE and same price and shopping cost, Basket has limit / (price + shipping)")
        void equalItems() {
            final BigDecimal PRICE_LIMIT = BigDecimal.valueOf(50);
            final BigDecimal SHOPPING_COST = BigDecimal.valueOf(2);
            final BigDecimal PRICE = BigDecimal.ONE;
            final int RATE = 1;

            List<Item> items = new ArrayList<>();
            for (int i = 1; i < 50; i++) {
                Item item = new Item(i, "test_" + i, PRICE, SHOPPING_COST, RATE, 10);
                items.add(item);
            }

            BigDecimal TOTAL_COST = PRICE.add(SHOPPING_COST);
            final int EXPECTED_RESULT_COUNT = PRICE_LIMIT.divide(TOTAL_COST, 0, RoundingMode.FLOOR).intValue();

            Optional<Basket> result = picker.selectItems(items, PRICE_LIMIT);
            assertTrue(result.isPresent(), "Result must have value");
            Basket basket = result.get();
            assertThat("", basket.getSumRating(), is(EXPECTED_RESULT_COUNT));
            assertThat("Total cost must not exceed limit", basket.getTotalCost(), lessThanOrEqualTo(PRICE_LIMIT));
            assertThat("", basket.getSelectedItems().size(), is(EXPECTED_RESULT_COUNT));
        }

        @Test
        @DisplayName("When items' cost not cover limit, all must be picked")
        void fewItems(){
            Item item1 = new Item(1, "test1", BigDecimal.valueOf(3), BigDecimal.valueOf(2), 1, 10); //5
            Item item2 = new Item(2, "test2", BigDecimal.valueOf(1), BigDecimal.valueOf(1), 2, 10); //2
            Item item3 = new Item(3, "test3", BigDecimal.valueOf(3), BigDecimal.valueOf(4), 3, 10); //7
            Item item4 = new Item(4, "test4", BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.5), 4, 10); //4
            Item item5 = new Item(5, "test5", BigDecimal.valueOf(1.75), BigDecimal.valueOf(0.75), 5, 10); //2.5
            List<Item> items = new ArrayList<>();
            items.add(item1);
            items.add(item2);
            items.add(item3);
            items.add(item4);
            items.add(item5);

            Optional<Basket> result = picker.selectItems(items, BigDecimal.valueOf(25));
            assertTrue(result.isPresent(), "Result must have value");
            Basket basket = result.get();
            assertThat("All items must be added, so sum rating is sum", basket.getSumRating(), is(15));
            assertTrue(Utils.isNumberEquals(basket.getTotalCost(),BigDecimal.valueOf(20.5)),
                    "All items must be added, so total cost is sum");
            assertThat("All items must be added", basket.getSelectedItems(), hasSize(items.size()));
        }
    }

}