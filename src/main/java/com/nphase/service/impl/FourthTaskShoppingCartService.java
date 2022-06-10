package com.nphase.service.impl;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public class FourthTaskShoppingCartService {

    private static final Integer NUMBER_PRODUCT_FOR_DISCOUNT = 3;
    private static final BigDecimal DISCOUNT_COEFF = new BigDecimal(0.9);

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
        boolean isDiscount = NUMBER_PRODUCT_FOR_DISCOUNT < quantity;

        BigDecimal resultPrice = price.multiply(new BigDecimal(quantity));
        if (isDiscount) {
            resultPrice = resultPrice.multiply(DISCOUNT_COEFF);
        }

        return resultPrice;
    }

}
