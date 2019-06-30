package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemPicker {

    BigDecimal calcItemPriority(Item item);
    Optional<Basket> selectItems(List<Item> items, BigDecimal priceLimit);
}
