package com.nphase.service.impl;

import com.nphase.entity.Product;
import com.nphase.entity.ProductCategory;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdTaskShoppingCartService {

    private static final Integer NUMBER_PRODUCT_FOR_DISCOUNT_IN_CATEGORY = 3;
    private static final BigDecimal DISCOUNT_COEFF = new BigDecimal("0.9");

    private final Map<ProductCategory, Integer> numberProductsByCategory = new HashMap<>();

    private final List<ProductCategory> productFordiscount = new ArrayList<>();

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {

        BigDecimal totalSum = BigDecimal.ZERO;
        List<Product> products = shoppingCart.getProducts();
        for (Product product : products) {
            final int quantity = product.getQuantity();
            final ProductCategory category = product.getCategory();

            Integer numberProducts = numberProductsByCategory.get(category);
            if (numberProducts != null && numberProducts > NUMBER_PRODUCT_FOR_DISCOUNT_IN_CATEGORY) {
                productFordiscount.add(category);
            } else {
                numberProducts = numberProducts + quantity;
                numberProductsByCategory.put(category, numberProducts);
            }
        }

        // TODO Refactoring to optimize performance to avoid double iterate over products list
        for (Product product : products) {

            final BigDecimal productSum = calculateProductSum(
                    product.getPricePerUnit(),
                    product.getQuantity(),
                    product.getCategory());

            totalSum = totalSum.add(productSum);
        }

        return totalSum;
    }

    private BigDecimal calculateProductSum(BigDecimal price, int quantity, ProductCategory category) {
        boolean isDiscount = productFordiscount.contains(category);

        BigDecimal resultPrice = price.multiply(new BigDecimal(quantity));
        if (isDiscount) {
            resultPrice = resultPrice.multiply(DISCOUNT_COEFF);
        }

        return resultPrice;
    }

}
