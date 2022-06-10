package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.impl.FirstTaskShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.nphase.entity.ProductCategory.DRINK;
import static com.nphase.entity.ProductCategory.FOOD;

public class FirstTaskShoppingCartServiceTest {

    private final ShoppingCartService service = new FirstTaskShoppingCartService();

    @Test
    public void calculatesPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, DRINK)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(16.5).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithZeroQuantity() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1, DRINK),
                new Product("Bread", BigDecimal.valueOf(7.0), 2, FOOD),
                new Product("Apple", BigDecimal.valueOf(5.5), 0, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(19.0).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithZeroPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 1, DRINK),
                new Product("Bread", BigDecimal.valueOf(0.0), 5, FOOD),
                new Product("Apple", BigDecimal.valueOf(5.5), 2, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(16.0).compareTo(result), 0);
    }

}
