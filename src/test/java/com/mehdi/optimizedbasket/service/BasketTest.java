package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.util.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasketTest {

    @Nested
    @DisplayName("Test Add item")
    class AddItemTests {

        @Test
        @DisplayName("Add one item less does not exceed limit")
        void addFirstItem() {
            Basket basket = new Basket(BigDecimal.TEN);
            Item item = new Item(1, "test", BigDecimal.valueOf(1), BigDecimal.valueOf(2), 1, 2);
            assertTrue(basket.addItem(item),
                    "Add one item less does not exceed limit, must return true");
            assertThat("By adding one item size must be 1",
                    basket.getSelectedItems(), hasSize(1));
            assertTrue(basket.getSelectedItems().contains(item),
                    "Added item must be in basket");

            assertThat("Item rating must be added to SumRating",
                    basket.getSumRating(), is(item.getRating()));
            assertTrue(Utils.isNumberEquals(basket.getTotalCost(), item.getPrice().add(item.getShippingCost())),
                    "Item shipping cost and price must be added to basket total cost");
        }

        @Test
        @DisplayName("Add an item to fill basket, total cost is exactly limit")
        void fillBasket() {
            Basket basket = new Basket(BigDecimal.TEN);
            Item item = new Item(1, "test", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 1, 2);
            assertTrue(basket.addItem(item),
                    "Add one item less does not exceed limit, must return true");
            assertThat("By adding one item size must be 1",
                    basket.getSelectedItems(), hasSize(1));
            assertTrue(basket.getSelectedItems().contains(item),
                    "Added item must be in basket");

            assertThat("Item rating must be added to SumRating",
                    basket.getSumRating(), is(item.getRating()));
            assertTrue(Utils.isNumberEquals(basket.getTotalCost(), item.getPrice().add(item.getShippingCost())),
                    "Item shipping cost and price must be added to basket total cost");
        }

        @Test
        @DisplayName("Add an item to a filled basket")
        void addWhenBasketIsFill() {
            Basket basket = new Basket(BigDecimal.TEN);
            Item item1 = new Item(1, "test", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 1, 2);
            Item item2 = new Item(2, "test2", BigDecimal.ONE, BigDecimal.ONE, 1, 2);
            basket.addItem(item1);

            assertFalse(basket.addItem(item2),
                    "Add one item which exceeds limit, must return false");
            assertThat("When fail to add item, size must be 1",
                    basket.getSelectedItems(), hasSize(1));
            assertFalse(basket.getSelectedItems().contains(item2),
                    "Not added item must not be in basket");

            assertThat("Item rating must be added to SumRating",
                    basket.getSumRating(), is(item1.getRating()));
            assertTrue(Utils.isNumberEquals(basket.getTotalCost(), item1.getPrice().add(item1.getShippingCost())),
                    "Item shipping cost and price must be added to basket total cost");
        }

        @Test
        @DisplayName("Add an item which exceed limit")
        void exceedLimit() {
            Basket basket = new Basket(BigDecimal.TEN);
            Item item1 = new Item(1, "test", BigDecimal.valueOf(5), BigDecimal.valueOf(2), 1, 2);
            Item item2 = new Item(2, "test2", BigDecimal.valueOf(3), BigDecimal.valueOf(7), 1, 2);
            basket.addItem(item1);

            assertFalse(basket.addItem(item2),
                    "Add one item which exceeds limit, must return false");
            assertThat("When fail to add item, size must be 1",
                    basket.getSelectedItems(), hasSize(1));
            assertFalse(basket.getSelectedItems().contains(item2),
                    "Not added item must not be in basket");

            assertThat("Item rating must be added to SumRating",
                    basket.getSumRating(), is(item1.getRating()));
            assertTrue(Utils.isNumberEquals(basket.getTotalCost(), item1.getPrice().add(item1.getShippingCost())),
                    "Item shipping cost and price must be added to basket total cost");
        }
    }

    @Nested
    @DisplayName("Test isFull()")
    class IsFullTests {

        @Test
        @DisplayName("When limit is not Zero, must return false")
        void initReturn() {
            // Given
            Basket basket = new Basket(BigDecimal.TEN);
            assertFalse(basket.isFull());
        }

        @Test
        @DisplayName("When limit is Zero, must return true")
        void initReturnZero() {
            // Given
            Basket basket = new Basket(BigDecimal.ZERO);
            assertTrue(basket.isFull());
        }

        @Test
        @DisplayName("When basket is full, must return true")
        void fullBasket() {
            // Given
            Basket basket = new Basket(BigDecimal.TEN);
            Item item = new Item(1, "test", BigDecimal.valueOf(8), BigDecimal.valueOf(2), 1, 2);
            basket.addItem(item);

            assertTrue(basket.isFull());
        }

        @Test
        @DisplayName("When basket is not full, must return false")
        void notFullBasket() {
            // Given
            Basket basket = new Basket(BigDecimal.TEN);
            Item item = new Item(1, "test", BigDecimal.valueOf(7), BigDecimal.valueOf(2), 1, 2);
            basket.addItem(item);

            assertFalse(basket.isFull());
        }
    }

}