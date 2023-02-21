package com.nphase.service;


import com.nphase.DiscountConfigPropertiesParser;
import com.nphase.discount.Discount;
import com.nphase.entity.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Properties;

public class ShoppingCartServiceTest {
    private ShoppingCartService service;

    @BeforeEach
    void setUp() throws IOException {
        DiscountConfigPropertiesParser discountParser = new DiscountConfigPropertiesParser();
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("application-test.properties"));
        Discount discount = discountParser.parse(properties);
        service = new ShoppingCartService(discount);
    }

    @Test
    public void calculatesPrice() {
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