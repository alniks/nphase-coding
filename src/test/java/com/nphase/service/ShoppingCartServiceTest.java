package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ProductCategory;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService(3, BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.1));

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, ProductCategory.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, ProductCategory.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void calculatesPriceWithDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, ProductCategory.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, ProductCategory.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(0, BigDecimal.valueOf(33).compareTo(result));
    }

    @Test
    public void calculatesPriceWithDiscountAndCategories()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, ProductCategory.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, ProductCategory.DRINKS),
                new Product("Cheese", BigDecimal.valueOf(8), 2, ProductCategory.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(0, BigDecimal.valueOf(31.84).compareTo(result));
    }

    @Test
    public void configurableDiscounts()  {
        ShoppingCartService service = new ShoppingCartService(3, BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.4));
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, ProductCategory.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, ProductCategory.DRINKS),
                new Product("Cheese", BigDecimal.valueOf(8), 2, ProductCategory.FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(0, BigDecimal.valueOf(26.56).compareTo(result));
    }
}