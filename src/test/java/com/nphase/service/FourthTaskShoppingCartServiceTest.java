package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.impl.FourthTaskShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.nphase.entity.ProductCategory.DRINK;
import static com.nphase.entity.ProductCategory.FOOD;

public class FourthTaskShoppingCartServiceTest {

    private final ShoppingCartService service = new FourthTaskShoppingCartService();

    @Test
    public void calculatesPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 7, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 2, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(41.0).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithoutDiscount() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 6, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 2, FOOD),
                new Product("Meat", BigDecimal.valueOf(7.5), 2, FOOD),
                new Product("Fish", BigDecimal.valueOf(9.0), 2, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(76.0).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithDiscountForOneCategory() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 3, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.0), 4, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 5, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(63.7).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithDiscountForAllCategories() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 4, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.0), 4, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 8, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(82.0).compareTo(result), 0);
    }

    @Test
    public void calculatesPriceWithZeroQuantityProduscts() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 0, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.0), 0, DRINK),
                new Product("Bread", BigDecimal.valueOf(6.5), 0, FOOD)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(BigDecimal.valueOf(0.0).compareTo(result), 0);
    }

}
