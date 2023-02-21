package com.nphase.service;

import com.nphase.entity.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {
    public static final BigDecimal DISCOUNT_FACTOR = BigDecimal.valueOf(0.9D);

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return groupByCategory(shoppingCart)
                .values().stream()
                .map(this::discountedPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private static Map<Category, List<Product>> groupByCategory(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts().stream().collect(
                Collectors.groupingBy(Product::getCategory));
    }

    private BigDecimal discountedPrice(List<Product> products) {
        return products.size() > 3
                ? totalPrice(products).multiply(DISCOUNT_FACTOR)
                : totalPrice(products);
    }

    private BigDecimal totalPrice(List<Product> products) {
        return products.stream()
                .map(it -> it.getPricePerUnit().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
