package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleReportGeneratorImpl implements ReportGenerator {
    @Override
    public void generate(Basket basket, List<Category> categories) {
        Map<Integer, Category> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getId(), category);
        }
        Map<Category, List<Item>> map = new HashMap<>();
        for (Item item : basket.getSelectedItems()) {
            int categoryId = item.getCategoryId();
            Category category  = categoryMap.get(categoryId);
            List<Item> items = null;
            if (map.containsKey(category)) {
                items = map.get(category);
            } else {
                items = new ArrayList<>();
            }
            items.add(item);
            map.put(category, items);
        }

        System.out.println("==============================================================");
        System.out.println("Total cost: ");
        System.out.println(basket.getTotalCost());
        System.out.println("Total rate: ");
        System.out.println(basket.getSumRating());
        System.out.println("==============================================================");
        for (Category category : map.keySet()) {
            System.out.println("Category: " + category.getName());
            for (Item item : map.get(category)) {
                System.out.println(item.getName());
            }
        }

    }
}
