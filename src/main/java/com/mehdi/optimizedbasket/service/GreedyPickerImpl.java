package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class GreedyPickerImpl implements ItemPicker {

    @Override
    public BigDecimal calcItemPriority(Item item) {
        return null;
    }

    @Override
    public Optional<Basket> selectItems(List<Item> items, BigDecimal priceLimit) {
        return Optional.empty();
    }
}
