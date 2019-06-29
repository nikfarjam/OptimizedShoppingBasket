package com.mehdi.optimizedbasket.model;

import java.math.BigDecimal;

/**
 * Configuration to build an item such as min and max value of a property
 * This class is a sample of Builder Design Pattern
 */
public class ItemValueRange implements ItemRange {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal minShoppingCost;
    private BigDecimal maxShoppingCost;
    private int minRate;
    private int maxRate;

    public ItemValueRange() {
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public ItemValueRange setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public ItemValueRange setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public BigDecimal getMinShoppingCost() {
        return minShoppingCost;
    }

    public ItemValueRange setMinShoppingCost(BigDecimal minShoppingCost) {
        this.minShoppingCost = minShoppingCost;
        return this;
    }

    public BigDecimal getMaxShoppingCost() {
        return maxShoppingCost;
    }

    public ItemValueRange setMaxShoppingCost(BigDecimal maxShoppingCost) {
        this.maxShoppingCost = maxShoppingCost;
        return this;
    }

    public int getMinRate() {
        return minRate;
    }

    public ItemValueRange setMinRate(int minRate) {
        this.minRate = minRate;
        return this;
    }

    public int getMaxRate() {
        return maxRate;
    }

    public ItemValueRange setMaxRate(int maxRate) {
        this.maxRate = maxRate;
        return this;
    }

    ItemRange getItemRange(){
        return (ItemRange)this;
    }
}
