package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.util.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Basket {
    private Map<Integer, Item> selectedItems;
    private BigDecimal totalCost;
    private BigDecimal costLimit;
    private int sumRating;

    public Basket(BigDecimal costLimit) {
        selectedItems = new HashMap<>();
        totalCost = BigDecimal.ZERO;
        this.costLimit = costLimit;
    }

    public Set<Item> getSelectedItems() {
        return new HashSet<>(selectedItems.values());
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public int getSumRating() {
        return sumRating;
    }

    public boolean addItem(Item item) {
        if (isFull()) {
            return false;
        }
        BigDecimal newTotalCost = totalCost.add(item.getPrice()).add(item.getShippingCost());
        if (newTotalCost.compareTo(costLimit) > 0) {
            return false;
        }
        totalCost = newTotalCost;
        this.selectedItems.put(item.getId(), item);
        sumRating += item.getRating();
        return true;
    }

    public boolean removeItem(Item item) {
        if (!selectedItems.containsKey(item.getId())) {
            return false;
        }
        this.selectedItems.remove(item.getId());
        totalCost = totalCost.subtract(item.getPrice()).subtract(item.getShippingCost());
        sumRating -= item.getRating();
        return true;
    }

    public boolean isFull() {
        return Utils.isNumberEquals(totalCost, costLimit);
    }
}
