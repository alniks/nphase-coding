package com.nphase.service;

import com.nphase.entity.ShoppingCart;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        Map<String, Integer> category = new HashMap<>();
        shoppingCart.getProducts().forEach(product -> {
            Integer count = product.getQuantity() + category.getOrDefault(product.getCategory(), 0);
            category.put(product.getCategory(), count);
        });

        return shoppingCart.getProducts().stream()
                .map(product -> {
                    BigDecimal price = category.get(product.getCategory()) <= 3
                            ? product.getPricePerUnit()
                            : product.getPricePerUnit().multiply(BigDecimal.valueOf(0.9d));
                    return price.multiply(BigDecimal.valueOf(product.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, (p, q) -> p.add(q));
    }
}
