package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GreedyPickerImpl implements ItemPicker {

    private static final Logger logger = LogManager.getLogger(GreedyPickerImpl.class);

    /**
     * Order items in descending order
     */
    private Comparator<Item> sortDescByWeight = (i1, i2) -> {
        BigDecimal weight1 = calcItemPriority(i1);
        BigDecimal weight2 = calcItemPriority(i2);
        return weight2.compareTo(weight1);
    };

    /**
     * Sort items by higher rate and lower total cost
     * then fill basket until it reaches total price limit
     *
     * @param items      list of items
     * @param priceLimit total price limit
     * @return a filled basket with items, having total rate and cost
     */
    @Override
    public Optional<Basket> selectItems(List<Item> items, BigDecimal priceLimit) {
        if (!isPriceLimitValid(priceLimit) || Utils.isEmptyCollection(items)) {
            return Optional.empty();
        }
        final Basket basket = new Basket(priceLimit);
        Optional<Item> overFlowItem = items.stream()
                .sorted(sortDescByWeight)
                .filter(i -> !basket.addItem(i))
                .findFirst();
        if (overFlowItem.isPresent()) {
            logger.info("Basket is full");
        }
        return Optional.of(basket);
    }

    private boolean isPriceLimitValid(BigDecimal priceLimit) {
        return priceLimit != null && priceLimit.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * Priorities items base on higher rate and lower total cost
     * Item priority is a number calculated by this formula
     * prating / (price + shopping cost)
     *
     * @param item given item
     * @return Item priority
     */
    @Override
    public BigDecimal calcItemPriority(Item item) {
        BigDecimal cost = item.getShippingCost().add(item.getPrice());
        return BigDecimal.valueOf(item.getRating()).divide(cost, 4, RoundingMode.HALF_DOWN);
    }

}
