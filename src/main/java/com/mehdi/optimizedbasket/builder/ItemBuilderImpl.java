package com.mehdi.optimizedbasket.builder;

import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.model.ItemRange;
import com.mehdi.optimizedbasket.model.ItemValueRange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ItemBuilderImpl implements ItemBuilder {

    /**
     * This values are used as name for items
     */
    private static final String[] ITEM_NAMES = {
            "ITEM_1", "ITEM_2", "ITEM_3", "ITEM_4", "ITEM_5", "ITEM_6", "ITEM_7", "ITEM_8", "ITEM_9",
            "ITEM_10", "ITEM_11", "ITEM_12", "ITEM_13", "ITEM_14", "ITEM_15", "ITEM_16", "ITEM_17",
            "ITEM_18", "ITEM_19", "ITEM_20",
    };

    /**
     * This values are used as name for categories
     */
    private static final String[] CATEGORY_NAMES = {
            "CAT_1", "CAT_2", "CAT_3", "CAT_4", "CAT_5", "CAT_6", "CAT_7", "CAT_8", "CAT_9", "CAT_10",
            "CAT_11", "CAT_12", "CAT_13", "CAT_14", "CAT_15", "CAT_16", "CAT_17", "CAT_18", "CAT_19",
            "CAT_20", "CAT_21", "CAT_22", "CAT_23", "CAT_24", "CAT_25",
    };


    private ItemRange valueRange;
    private Random rand;

    public ItemBuilderImpl() {
        // Null object
        valueRange = new ItemValueRange().setMinPrice(BigDecimal.ZERO)
                .setMaxPrice(BigDecimal.ZERO)
                .setMinShoppingCost(BigDecimal.ZERO)
                .setMaxShoppingCost(BigDecimal.ZERO)
                .setMinRate(0)
                .setMaxRate(0);
        rand = new Random();
    }

    /**
     * Set range for random generated values
     *
     * @param valueRange configuration for generated values
     */
    @Override
    public void setItemRange(ItemRange valueRange) {
        this.valueRange = valueRange;
    }

    @Override
    public List<Category> getCategoriesAndItems(int numOfCat, int itemForEachCategory) {
        List<Category> result = new ArrayList<>(numOfCat);
        for (int i = 0; i < numOfCat; i++) {
            int categoryId = 100 + i;
            Category category = getCategory(categoryId, CATEGORY_NAMES[i]);

            List<Item> items = new ArrayList<>(itemForEachCategory);
            for (int j = 0; j < itemForEachCategory; j++) {
                int itemId = i * 10 + j;
                String itemName = CATEGORY_NAMES[i] + "." + ITEM_NAMES[j];
                Item item = createRandomItem(itemId, itemName);
                item.setCategoryId(categoryId);
                items.add(item);
            }

            category.setItems(items);
            result.add(category);
        }
        return result;
    }

    @Override
    public Item createRandomItem(int id, String name) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setPrice(getRandomBigDecimal(valueRange.getMinPrice(), valueRange.getMaxPrice()));
        item.setShippingCost(getRandomBigDecimal(valueRange.getMinShoppingCost(), valueRange.getMaxShoppingCost()));
        item.setRating(getRandomInt(valueRange.getMinRate(), valueRange.getMaxRate()));
        return item;
    }

    @Override
    public Category getCategory(int id, String name) {
        Category cat = new Category();
        cat.setId(id);
        cat.setName(name);
        return cat;
    }

    private int getRandomInt(int min, int max) {
        return min + rand.nextInt(max - min);
    }

    private BigDecimal getRandomBigDecimal(BigDecimal min, BigDecimal max) {
        double bound = max.subtract(min).doubleValue();
        BigDecimal randomNumber = BigDecimal.valueOf(Math.round(bound * rand.nextDouble()));
        return min.add(randomNumber.setScale(2, RoundingMode.HALF_DOWN));
    }
}
