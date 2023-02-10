package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartService {

    private final int DISCOUNT_REWARD_QUANTITY = 3;
    private final int DISCOUNT_CATEGORY_ITEMS_COUNT = 3;

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {

        if (shoppingCart == null || shoppingCart.getProducts() == null) {
            return BigDecimal.ZERO;
        }

        List<Product> products = shoppingCart.getProducts();
        Map<String, Integer> categoryItemCounts = getCategoryItemCounts(products);

        BigDecimal result = BigDecimal.ZERO;
        for (Product product : products) {
            String categoryCode = product.getCategory().getCode();

            BigDecimal productSum = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
            if (product.getQuantity() > DISCOUNT_REWARD_QUANTITY) {
                productSum = productSum.multiply(BigDecimal.valueOf(0.9));
            }

            if (categoryItemCounts.get(categoryCode) > DISCOUNT_CATEGORY_ITEMS_COUNT) {
                productSum = productSum.multiply(BigDecimal.valueOf(0.9));
            }
            result = result.add(productSum);
        }

        return result;
    }

    private Map<String, Integer> getCategoryItemCounts(List<Product> products) {
        Map<String, Integer> result = new HashMap<>();
        for (Product product : products) {
            String categoryCode = product.getCategory().getCode();

            Integer categoryProductCount = result.get(categoryCode);
            if (categoryProductCount == null) {
                categoryProductCount = 0;
            }
            result.put(categoryCode, categoryProductCount + product.getQuantity());
        }
        return result;
    }

}
