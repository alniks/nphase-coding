package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.impl.SecondTaskShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.nphase.entity.ProductCategory.DRINK;
import static com.nphase.entity.ProductCategory.FOOD;

public class SecondTaskShoppingCartServiceTest {

    private final ShoppingCartService service = new SecondTaskShoppingCartService();

    @Test
    public void calculatesPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.5), 2, DRINK)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(35.5).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWhenAllCategoriesHaveDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 10, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.5), 4, DRINK)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(68.4).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWhenNoAnyDiscounts() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.5), 3, FOOD),
                new Product("Milk", BigDecimal.valueOf(9.5), 0, DRINK),
                new Product("Burger", BigDecimal.valueOf(7.0), 3, FOOD),
                new Product("Chocolate", BigDecimal.valueOf(3.0), 2, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(51.5).compareTo(result), 0);
    }

}
