package com.nphase.service;

import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;

public class ShoppingCartService {
    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts().stream()
                .map(it -> it.getPricePerUnit().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
