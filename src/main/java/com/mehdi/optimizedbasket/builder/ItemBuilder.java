package com.mehdi.optimizedbasket.builder;

import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.model.ItemRange;

import java.util.List;

public interface ItemBuilder {

    void setItemRange(ItemRange valueRange);

    List<Category> getCategoriesAndItems(int numOfCat, int itemForEachCategory);

    Item createRandomItem(int id, String name);

    Category getCategory(int id, String name);
}
