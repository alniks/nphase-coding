package com.nphase.service;

import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;

public interface ShoppingCartService {

    BigDecimal calculateTotalPrice(ShoppingCart shoppingCart);

}
