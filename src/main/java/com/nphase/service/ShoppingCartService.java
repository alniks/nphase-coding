package com.nphase.service;

import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShoppingCartService {
    private static final double DISCOUNT_FACTOR = 0.9D;

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = shoppingCart.getProducts().stream()
                .map(it -> it.getPricePerUnit().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal finaPrice = shoppingCart.getProducts().size() > 3
                ? totalPrice.multiply(BigDecimal.valueOf(DISCOUNT_FACTOR))
                : totalPrice;

        return finaPrice.setScale(2, RoundingMode.HALF_UP);
    }
}
