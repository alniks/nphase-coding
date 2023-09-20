package com.nphase.service;

import com.nphase.config.DiscountConfig;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {
    private static final DiscountConfig DISCOUNT_CONFIG = new DiscountConfig();

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts().stream()
                .map(product ->
                        product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalPriceWithDiscount(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts().stream()
                .map(product -> {
                    BigDecimal basePrice = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                    if (product.getQuantity() > DISCOUNT_CONFIG.getAmount()) {
                        basePrice = basePrice.multiply(BigDecimal.valueOf(DISCOUNT_CONFIG.getValue()));
                    }
                    return basePrice;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalPriceWithCategoryDiscount(ShoppingCart shoppingCart) {
        Map<String, Integer> priceByCategory = shoppingCart.getProducts().stream()
                .collect(Collectors.toMap(Product::getCategory, Product::getQuantity, Integer::sum));
        return shoppingCart.getProducts().stream()
                .map(product -> {
                    BigDecimal basePrice = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                    if (priceByCategory.get(product.getCategory()) > DISCOUNT_CONFIG.getAmount()) {
                        basePrice = basePrice.multiply(BigDecimal.valueOf(DISCOUNT_CONFIG.getValue()));
                    }
                    return basePrice;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
