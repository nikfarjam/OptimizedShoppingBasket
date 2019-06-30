package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleReportGeneratorImpl implements ReportGenerator {

    /**
     * Print result on Console
     * Time complexity is O(n + m * lon(n)), n: number of items, m: number of categories
     *
     * @param basket     Result of project
     * @param categories a list of all categories to print their names
     */
    @Override
    public void generate(Basket basket, List<Category> categories) {
        Map<Integer, List<Item>> categoryAndItems = createMapOfCategoryAndItems(basket);
        printHeader(basket);
        printCategoriesAndItems(categories, categoryAndItems);
    }

    private void printHeader(Basket basket) {
        System.out.println("==============================================================");
        System.out.println("Total cost: ");
        System.out.println(basket.getTotalCost());
        System.out.println("Total rate: ");
        System.out.println(basket.getSumRating());
        System.out.println("==============================================================");
    }

    private void printCategoriesAndItems(List<Category> categories, Map<Integer, List<Item>> map) {
        for (Category category : categories) {
            if (map.containsKey(category.getId())) {
                System.out.println("Category: " + category.getName());
                for (Item item : map.get(category.getId())) {
                    System.out.println(item.getName());
                }
            }
        }
    }

    private Map<Integer, List<Item>> createMapOfCategoryAndItems(Basket basket) {
        Map<Integer, List<Item>> map = new HashMap<>();
        for (Item item : basket.getSelectedItems()) {
            int categoryId = item.getCategoryId();
            List<Item> items = null;
            if (map.containsKey(categoryId)) {
                items = map.get(categoryId);
            } else {
                items = new ArrayList<>();
            }
            items.add(item);
            map.put(categoryId, items);
        }
        return map;
    }
}
