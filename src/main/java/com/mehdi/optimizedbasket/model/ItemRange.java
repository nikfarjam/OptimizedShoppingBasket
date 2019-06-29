package com.mehdi.optimizedbasket.model;

import java.math.BigDecimal;

public interface ItemRange {

    BigDecimal getMinPrice();
    BigDecimal getMaxPrice();
    BigDecimal getMinShoppingCost();
    BigDecimal getMaxShoppingCost();
    int getMinRate();
    int getMaxRate();
}
