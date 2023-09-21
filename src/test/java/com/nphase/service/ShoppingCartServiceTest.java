package com.nphase.service;


import com.nphase.entity.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, Category.FOOD),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, Category.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void calculatesDiscountPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, Category.FOOD),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, Category.FOOD)
        ));

        BigDecimal result = service.calculateDiscountTotalPrice(cart);

        Assertions.assertEquals("33.00", String.valueOf(result));
    }

    @Test
    public void calculatesCategoryDiscountPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, Category.DRINKS),
                new Product("Cheese", BigDecimal.valueOf(8), 2, Category.FOOD),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, Category.DRINKS)
        ));

        BigDecimal result = service.calculateCategoryDiscountTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(31.84), result);
    }

}