package com.mehdi.optimizedbasket.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private BigDecimal price;
    private BigDecimal shippingCost;
    private int rating;
    private int categoryId;

    public Item() {
    }

    public Item(int id, String name, BigDecimal price, BigDecimal shippingCost, int rating, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.shippingCost = shippingCost;
        this.rating = rating;
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Id of category for this item
     */
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id == item.id &&
                rating == item.rating &&
                categoryId == item.categoryId &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price) &&
                Objects.equals(shippingCost, item.shippingCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, shippingCost, rating, categoryId);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", shippingCost=").append(shippingCost);
        sb.append(", rating=").append(rating);
        sb.append(", categoryId=").append(categoryId);
        sb.append('}');
        return sb.toString();
    }
}
