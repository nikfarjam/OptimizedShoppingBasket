package com.mehdi.optimizedbasket.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

public class Utils {

    /**
     * private constructor to avoid method override
     */
    private Utils() {
    }

    /**
     *
     * @param c a collection
     * @return true if collection is null or empty
     */
    public static boolean isEmptyCollection(Collection c) {
        return c == null || c.isEmpty();
    }

    /**
     *
     * @param num1
     * @param num2
     * @return true if both has exactly the same value
     */
    public static boolean isNumberEquals(BigDecimal num1, BigDecimal num2) {
        if (num1 == null && num2 == null){
            return true;
        }
        if ((num1 == null && num2 != null) || (num2 == null && num1 != null)) {
            return false;
        }
        return Objects.requireNonNull(num1).compareTo(num2) == 0;
    }
}
