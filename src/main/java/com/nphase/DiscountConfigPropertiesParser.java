package com.nphase;

import com.nphase.discount.Discount;
import com.nphase.entity.Category;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

public class DiscountConfigPropertiesParser {
    public Discount parse(Properties properties) {
        return new Discount(
                Integer.parseInt(properties.getProperty("shop.discount.min_products_count_for_discount")),
                Map.of(
                        Category.food, new BigDecimal(properties.getProperty(categoryPropName(Category.food))),
                        Category.drinks, new BigDecimal(properties.getProperty(categoryPropName(Category.drinks)))
                )
        );
    }

    private static String categoryPropName(Category category) {
        return "shop.discount.categories." + category.name();
    }
}
