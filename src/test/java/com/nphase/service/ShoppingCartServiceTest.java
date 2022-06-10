package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.impl.FirstTaskShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.nphase.entity.ProductCategory.DRINK;

public class ShoppingCartServiceTest {
    private final FirstTaskShoppingCartService service = new FirstTaskShoppingCartService();

    @Test
    public void calculatesPrice() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, DRINK),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, DRINK)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

}
