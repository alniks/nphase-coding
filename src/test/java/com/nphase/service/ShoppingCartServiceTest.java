package com.nphase.service;


import com.nphase.entity.Category;
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
                new Product("Tea", Category.food, BigDecimal.valueOf(5.0), 2),
                new Product("Coffee", Category.food, BigDecimal.valueOf(6.5), 1)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(16.5).setScale(2, RoundingMode.HALF_UP), result);
    }

    @Test
    void calculatePriceWithDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", Category.food, BigDecimal.valueOf(2.5D), 1),
                new Product("Coffee", Category.food, BigDecimal.valueOf(1), 1),
                new Product("Cake", Category.drinks, BigDecimal.valueOf(4), 1),
                new Product("Apple", Category.drinks, BigDecimal.valueOf(2.5D), 1)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), result);
    }

    @Test
    void calculatePriceWithCategoryDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", Category.food, BigDecimal.valueOf(3), 1),
                new Product("Coffee", Category.drinks, BigDecimal.valueOf(4), 1),
                new Product("Cake", Category.drinks, BigDecimal.valueOf(1), 1),
                new Product("Cake", Category.drinks, BigDecimal.valueOf(4), 1),
                new Product("Apple", Category.drinks, BigDecimal.valueOf(1), 1)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(12).setScale(2, RoundingMode.HALF_UP), result);
    }
}