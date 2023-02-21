package com.nphase.discount;

import com.nphase.entity.Category;

import java.math.BigDecimal;
import java.util.Map;

public class Discount {
    private final int minProductsCountForDiscount;
    private final Map<Category, BigDecimal> discountByCategory;

    public Discount(final int productsCountEligibleForDiscount,
                    final Map<Category, BigDecimal> discountByCategory) {
        this.minProductsCountForDiscount = productsCountEligibleForDiscount;
        this.discountByCategory = Map.copyOf(discountByCategory);
    }

    public BigDecimal resolveDiscountMultiplier(final Category category) {
        return BigDecimal.ONE.subtract(resolveDiscount(category));
    }

    public BigDecimal resolveDiscount(final Category category) {
        return discountByCategory.getOrDefault(category, BigDecimal.ZERO);
    }

    public int minProductsCountForDiscount() {
        return minProductsCountForDiscount;
    }
}
