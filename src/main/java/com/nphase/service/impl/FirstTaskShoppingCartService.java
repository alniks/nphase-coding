package com.nphase.service.impl;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public class FirstTaskShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {

        BigDecimal totalSum = BigDecimal.ZERO;
        List<Product> products = shoppingCart.getProducts();
        for (Product product : products) {
            final BigDecimal productSum = calculateProductSum(product.getPricePerUnit(), product.getQuantity());
            totalSum = totalSum.add(productSum);
        }

        return totalSum;
    }

    private BigDecimal calculateProductSum(BigDecimal price, int quantity) {
        return price.multiply(new BigDecimal(quantity));
    }

}
