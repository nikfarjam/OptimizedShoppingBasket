package com.mehdi.optimizedbasket.builder;

import com.mehdi.optimizedbasket.factory.ClassFactory;
import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.model.ItemValueRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class ItemBuilderTest {

    private static final int CATEGORIES_COUNT = 20;
    private static final int ITEM_COUNTS = 10;
    private static ItemValueRange TEST_RANGE = new ItemValueRange()
            .setMinPrice(BigDecimal.ONE)
            .setMaxPrice(BigDecimal.valueOf(20))
            .setMinShoppingCost(BigDecimal.valueOf(2))
            .setMaxShoppingCost(BigDecimal.valueOf(5))
            .setMinRate(1)
            .setMaxRate(5);

    private ItemBuilder builder;

    @BeforeEach
    void setUp() {
        builder = ClassFactory.getInstance().createItemBuilder(TEST_RANGE.getItemRange());
    }

    @Nested
    @DisplayName("Test generated items and categories")
    class GeneratedItemsAndCategoriesTest {
        @Test
        @DisplayName("Generator creates categories")
        void testCategoriesNotNull() {
            List<Category> categories = builder.getCategoriesAndItems(CATEGORIES_COUNT, ITEM_COUNTS);
            assertNotNull(categories, "Categories must not be null");
        }

        @Test
        @DisplayName("Check if builder creates exactly " + CATEGORIES_COUNT + " categories")
        void categoriesCount() {
            List<Category> categories = builder.getCategoriesAndItems(CATEGORIES_COUNT, ITEM_COUNTS);
            // fill a set with categories
            Set<Category> set = new HashSet<>(categories);

            int count = set.size();
            assertEquals(CATEGORIES_COUNT, count, "Generator must create " + CATEGORIES_COUNT + " categories");
        }

        @Test
        @DisplayName("Check if builder creates items for each category")
        void itemsNotNull() {
            List<Category> categories = builder.getCategoriesAndItems(CATEGORIES_COUNT, ITEM_COUNTS);
            for (Category category : categories) {
                assertNotNull(category.getItems(), "Items in each category must not be null");
            }
        }

        @Test
        @DisplayName("Check if builder creates exactly " + ITEM_COUNTS + " items for each category")
        void itemsCount() {
            List<Category> categories = builder.getCategoriesAndItems(CATEGORIES_COUNT, ITEM_COUNTS);
            for (Category category : categories) {
                Set<Item> set = new HashSet<>(category.getItems());
                assertThat(set, hasSize(ITEM_COUNTS));
            }
        }
    }
}