package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.util.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GreedyPickerImpl implements ItemPicker {

    private Comparator<Item> sortDescByWeight = (i1, i2) -> {
        BigDecimal weight1 = calcItemPriority(i1);
        BigDecimal weight2 = calcItemPriority(i2);
        return weight2.compareTo(weight1);
    };

    @Override
    public Optional<Basket> selectItems(List<Item> items, BigDecimal priceLimit) {
        if (priceLimit == null || priceLimit.compareTo(BigDecimal.ZERO) == 0) {
            return Optional.empty();
        }
        if (Utils.isEmptyCollection(items)) {
            return Optional.empty();
        }
        final Basket basket = new Basket(priceLimit);
        Optional<Item> overFlowItem = items.stream()
                .sorted(sortDescByWeight)
                .filter(i -> !basket.addItem(i))
                .findFirst();
        if (overFlowItem.isPresent()) {
            return Optional.of(basket);
        }
        return Optional.of(basket);
    }

    @Override
    public BigDecimal calcItemPriority(Item item) {
        BigDecimal cost = item.getShippingCost().add(item.getPrice());
        return BigDecimal.valueOf(item.getRating()).divide(cost, 4, RoundingMode.HALF_DOWN);
    }

}
