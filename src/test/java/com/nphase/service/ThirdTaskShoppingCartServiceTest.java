package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.impl.ThirdTaskShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.nphase.entity.ProductCategory.DRINK;
import static com.nphase.entity.ProductCategory.FOOD;

public class ThirdTaskShoppingCartServiceTest {

    private final ShoppingCartService service = new ThirdTaskShoppingCartService();

    @Test
    public void calculatesPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 2, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(35.5).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithoutDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 3, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 1, FOOD),
                new Product("Meat", BigDecimal.valueOf(7.5), 1, FOOD),
                new Product("Fish", BigDecimal.valueOf(9.0), 1, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(38.0).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithDiscountForOneCategory() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.0), 2, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 2, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(32.8).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithDiscountForAllCategories() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.0), 2, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 5, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(49.05).compareTo(result), 0);
    }

}
