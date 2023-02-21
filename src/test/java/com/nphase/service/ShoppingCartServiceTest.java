package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(16.5).setScale(2, RoundingMode.HALF_UP), result);
    }

    @Test
    void calculatePriceWithDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(2.5D), 1),
                new Product("Coffee", BigDecimal.valueOf(1), 1),
                new Product("Cake", BigDecimal.valueOf(4), 1),
                new Product("Apple", BigDecimal.valueOf(2.5D), 1)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(9).setScale(2, RoundingMode.HALF_UP), result);
    }
}